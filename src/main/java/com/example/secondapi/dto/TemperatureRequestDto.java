package com.example.secondapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureRequestDto {

    private String cityName;

    private Double cityTemperature;

    private Long dateOfRequest;

}
