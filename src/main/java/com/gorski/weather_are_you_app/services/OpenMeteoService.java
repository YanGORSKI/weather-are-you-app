package com.gorski.weather_are_you_app.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.clients.OpenMeteoClient;
import com.gorski.weather_are_you_app.contexts.WeatherCacheContext;
import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.responses.OpenMeteoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OpenMeteoService {

    private OpenMeteoClient openMeteoClient;
    private WeatherCacheContext cacheContext;

    @Cacheable(
        value = "weatherCache",
        key = "#coord.latitude + '-' + #coord.longitude"
    )
    public OpenMeteoResponse getWeatherForecastFromCoordinates(CoordinatesDTO coord) {
        System.out.println("Fetching weather forecast for coordinates: " + coord);
        cacheContext.markAsFresh();
        
        return openMeteoClient.getForecastFromCoordinates(coord);
    }
}
