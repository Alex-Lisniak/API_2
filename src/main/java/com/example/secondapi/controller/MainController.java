package com.example.secondapi.controller;


import com.example.secondapi.dto.TemperatureRequestDto;
import com.example.secondapi.service.MainService;
import com.example.secondapi.service.TemperatureRequestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    private final TemperatureRequestService temperatureRequestService;

    @GetMapping("/city/getTemperature/{cityName}")
    public String getTemperature(@PathVariable String cityName){

        try {
             return mainService.getTemperature(cityName);
        }catch(URISyntaxException  | ExecutionException | InterruptedException | IOException exception){
            System.out.println(exception.getMessage());
        }
        return null;

    }
    @Operation
    @GetMapping("/request/getAllInPeriod/{lowBound}/{highBound}")
    public List<TemperatureRequestDto> getAllRequestsInPeriod(@PathVariable Long lowBound, @PathVariable Long highBound){
        return temperatureRequestService.getAllRequestsInPeriod(lowBound, highBound);
    }
}
