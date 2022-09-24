package com.example.secondapi.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.UUID;
import java.util.concurrent.ExecutionException;



@Service
public class MainServiceImpl implements MainService{

    @Override
    public String getTemperature(String cityName) throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .followRedirects(Redirect.NORMAL)
                .build();

        HttpRequest getKeyRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/city/key"))
                .GET()
                .build();

        String stringKeyResponse =  client.sendAsync(getKeyRequest, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();
        UUID uuidKeyResponse = UUID.fromString(
                stringKeyResponse.substring(1, stringKeyResponse.length() - 1));

        HttpRequest getCityRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/city/" + cityName))
                .GET()
                .header("key", String.valueOf(uuidKeyResponse))
                .build();

        String cityResponse = client.sendAsync(getCityRequest, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

        if(Boolean.valueOf(cityResponse)){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location="+ cityName + "&contentType=csv&unitGroup=us&shortColumnNames=true"))
                    .header("X-RapidAPI-Key", "23a61627c3msh358b27a79119c86p127f69jsn8007c25c0909")
                    .header("X-RapidAPI-Host", "visual-crossing-weather.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


            Double temperature = getCurrentTemperatureFromResponse(httpResponse.body(),cityName);
            System.out.println(temperature);

        }


        return null;
    }
    private Double getCurrentTemperatureFromResponse(String response, String cityName){
        String newOne = response.substring(182 + cityName.length()+ 2 + 29 ,response.length()-1 );
        String stringWithoutCity = newOne.replaceFirst("\"(\\w){3}", "");
        int indexOfCity = stringWithoutCity.indexOf(cityName);

        String stringWithoutCityName = stringWithoutCity.substring(indexOfCity+cityName.length()+2, stringWithoutCity.length()-1);

        String stringOfTemps = stringWithoutCityName.replaceFirst("(\\d{1,3}\\.\\d{1,3},){3}", "");

        return Double.parseDouble(stringOfTemps.substring(0,stringOfTemps.indexOf('.')+2));
    }
}
