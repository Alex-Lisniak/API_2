package com.example.secondapi.service;

import com.example.secondapi.dto.TemperatureRequestDto;
import com.example.secondapi.entity.TemperatureRequest;
import com.example.secondapi.mapper.TemperatureRequestMapper;
import com.example.secondapi.repository.TemperatureRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemperatureRequestServiceImpl implements TemperatureRequestService {

    private final TemperatureRequestRepository temperatureRequestRepository;

    private final TemperatureRequestMapper temperatureRequestMapper;

    @Override
    public List<TemperatureRequestDto> getAllRequestsInPeriod(Long lowBound, Long highBound) {
        List <TemperatureRequest> temperatureRequests = temperatureRequestRepository.findAll();

        return temperatureRequestMapper.listToDto(temperatureRequests.stream().
                filter( request -> request.getDateOfRequest() > lowBound && request.getDateOfRequest() < highBound).collect(Collectors.toList()));
    }
}
