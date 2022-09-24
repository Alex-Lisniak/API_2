package com.example.secondapi.controller;


import com.example.secondapi.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/city/getTemperature/{cityName}")
    public String getCity(@PathVariable String cityName){

        try {
            return mainService.getTemperature(cityName);
        }catch(URISyntaxException  | ExecutionException | InterruptedException | IOException exception){
            System.out.println(exception.getMessage());
        }
        throw new RuntimeException("get Temperature service isn`t working correct");
    }
}
