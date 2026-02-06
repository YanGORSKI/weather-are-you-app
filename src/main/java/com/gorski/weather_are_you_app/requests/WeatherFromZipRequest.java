package com.gorski.weather_are_you_app.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherFromZipRequest {
    private String zipCode;
    private Boolean additionalInfo;
    private Boolean extendedForecast;
}
