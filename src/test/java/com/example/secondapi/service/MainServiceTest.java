package com.example.secondapi.service;


import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;

public class MainServiceTest {

    @Test
    public void getTemperature(){
        WireMockServer wireMockServer = new WireMockServer();

        wireMockServer.start();


        wireMockServer.stop();
    }
}
