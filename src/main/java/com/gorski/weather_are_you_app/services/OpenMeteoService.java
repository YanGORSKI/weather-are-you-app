package com.gorski.weather_are_you_app.services;

import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.clients.OpenMeteoClient;
import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.responses.OpenMeteoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
// Use Cacheable here
// Exception handling for client not responding
// Exception handling for invalid coordinates or no results
public class OpenMeteoService {

    private OpenMeteoClient openMeteoClient;

    public OpenMeteoResponse getWeatherForecastFromCoordinates(CoordinatesDTO coord) {
        return openMeteoClient.getForecastFromCoordinates(coord);
    }
}
