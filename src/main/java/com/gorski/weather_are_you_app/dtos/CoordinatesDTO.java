package com.gorski.weather_are_you_app.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoordinatesDTO {
    private Double latitude;
    private Double longitude;
}
