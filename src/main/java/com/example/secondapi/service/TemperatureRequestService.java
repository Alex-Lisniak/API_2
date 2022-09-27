package com.example.secondapi.service;

import com.example.secondapi.dto.TemperatureRequestDto;

import java.util.List;

public interface TemperatureRequestService {
    List<TemperatureRequestDto> getAllRequestsInPeriod(Long lowBound, Long highBound);
}
