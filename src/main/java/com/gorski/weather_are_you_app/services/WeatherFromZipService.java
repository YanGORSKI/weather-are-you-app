package com.gorski.weather_are_you_app.services;

import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.assemblers.WeatherFromZipResponseAssembler;
import com.gorski.weather_are_you_app.requests.WeatherFromZipRequest;
import com.gorski.weather_are_you_app.responses.WeatherFromZipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherFromZipService {

    private final GeoNamesService geoNamesService;

    private final OpenMeteoService openMeteoService;

    private final WeatherFromZipResponseAssembler assembler;

    public WeatherFromZipResponse getWeatherFromZip(WeatherFromZipRequest request) {

        var coordinates = geoNamesService.getCoordinatesFromZipCode(request.getZipCode());
        
        var weatherForecast = openMeteoService.getWeatherForecastFromCoordinates(coordinates);

        return assembler.assembleResponse(request, weatherForecast);
    }

}