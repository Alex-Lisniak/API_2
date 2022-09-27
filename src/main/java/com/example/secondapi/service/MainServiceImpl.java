package com.example.secondapi.service;

import com.example.secondapi.entity.TemperatureRequest;
import com.example.secondapi.repository.TemperatureRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

    public static final int TWENTY_MINITES_IN_MILISECONDS = 1200000;

    @Value("${weather.service.header.key.name}")
    private String weatherServiceHeaderKeyName;

    @Value("${weather.service.header.key.value}")
    private String weatherServiceHeaderKeyValue;

    @Value("${weather.service.header.host.name}")
    private String weatherServiceHeaderHostName;

    @Value("${weather.service.header.host.value}")
    private String weatherServiceHeaderHostValue;

    private final String API_1_URI = "http://localhost:8090/city/";
    private final TemperatureRequestRepository temperatureRequestRepository;

    @Override
    public String getTemperature(String cityName) throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .followRedirects(Redirect.NORMAL)
                .build();

        HttpRequest getKeyRequest = HttpRequest.newBuilder()
                .uri(new URI(API_1_URI + "key"))
                .GET()
                .build();

        String stringKeyResponse =  client.sendAsync(getKeyRequest, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();
        UUID uuidKeyResponse = UUID.fromString(
                stringKeyResponse.substring(1, stringKeyResponse.length() - 1));

        HttpRequest getCityRequest = HttpRequest.newBuilder()
                .uri(new URI(API_1_URI + cityName))
                .GET()
                .header("key", String.valueOf(uuidKeyResponse))
                .build();

        String cityResponse = client.sendAsync(getCityRequest, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

        if(Boolean.valueOf(cityResponse)){
            Long requestDate = new Date().getTime();
            String response = getStringResponseFromWeatherApi(cityName);

            Double temperature = getCurrentTemperatureFromResponse(response);
            System.out.println(temperature);
            temperatureRequestRepository.save(TemperatureRequest.builder()
                            .cityName(cityName)
                            .cityTemperature(temperature)
                            .dateOfRequest(requestDate)
                            .TTL((requestDate+ TWENTY_MINITES_IN_MILISECONDS)/1000) //  TTL is working with seconds, so milliseconds value should be divide
                            .build());
            return temperature.toString();

        }
        return null;
    }

    private String getStringResponseFromWeatherApi(String cityName) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location="+ cityName + "&contentType=csv&unitGroup=us&shortColumnNames=true"))
                .header(weatherServiceHeaderKeyName, weatherServiceHeaderKeyValue)
                .header(weatherServiceHeaderHostName, weatherServiceHeaderHostValue)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return  httpResponse.body();
    }

    private Double getCurrentTemperatureFromResponse(String response){
        int indexOfTemp = response.indexOf("temp");

        String firstRowString = response.substring(0, indexOfTemp);

        int countOfCommas = firstRowString.replaceAll("[^,]","").length();

        String responseWithoutNaming = response.substring(response.indexOf("\n"), response.length()-1);

        String stringWithoutCityNameAndAddress = responseWithoutNaming.replaceFirst("([^,]*,){" + (countOfCommas+1) + "}", "");

        return Double.parseDouble(stringWithoutCityNameAndAddress.substring(0,stringWithoutCityNameAndAddress.indexOf('.')+2));

    }

}
// todo - optimize regex or provide CSVReader