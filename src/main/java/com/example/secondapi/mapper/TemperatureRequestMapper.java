package com.example.secondapi.mapper;

import com.example.secondapi.dto.TemperatureRequestDto;
import com.example.secondapi.entity.TemperatureRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemperatureRequestMapper {

    TemperatureRequestMapper INSTANCE = Mappers.getMapper(TemperatureRequestMapper.class);

    TemperatureRequestDto toDto(TemperatureRequest temperatureRequest);

    List<TemperatureRequestDto> listToDto(List<TemperatureRequest> depositOfferList);

}
