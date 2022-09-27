package com.example.secondapi.mapper;

import com.example.secondapi.dto.TemperatureRequestDto;
import com.example.secondapi.entity.TemperatureRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T14:36:53+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
@Component
public class TemperatureRequestMapperImpl implements TemperatureRequestMapper {

    @Override
    public TemperatureRequestDto toDto(TemperatureRequest temperatureRequest) {
        if ( temperatureRequest == null ) {
            return null;
        }

        TemperatureRequestDto temperatureRequestDto = new TemperatureRequestDto();

        temperatureRequestDto.setCityName( temperatureRequest.getCityName() );
        temperatureRequestDto.setCityTemperature( temperatureRequest.getCityTemperature() );
        temperatureRequestDto.setDateOfRequest( temperatureRequest.getDateOfRequest() );

        return temperatureRequestDto;
    }

    @Override
    public List<TemperatureRequestDto> listToDto(List<TemperatureRequest> depositOfferList) {
        if ( depositOfferList == null ) {
            return null;
        }

        List<TemperatureRequestDto> list = new ArrayList<TemperatureRequestDto>( depositOfferList.size() );
        for ( TemperatureRequest temperatureRequest : depositOfferList ) {
            list.add( toDto( temperatureRequest ) );
        }

        return list;
    }
}
