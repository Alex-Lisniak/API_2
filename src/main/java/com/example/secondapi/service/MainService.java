package com.example.secondapi.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public interface MainService {

    String getTemperature(String cityName) throws URISyntaxException, ExecutionException, InterruptedException, IOException;
}
