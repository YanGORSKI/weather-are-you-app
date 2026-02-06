package com.gorski.weather_are_you_app.services;

import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.assemblers.WeatherFromZipResponseAssembler;
import com.gorski.weather_are_you_app.contexts.WeatherCacheContext;
import com.gorski.weather_are_you_app.requests.WeatherFromZipRequest;
import com.gorski.weather_are_you_app.responses.WeatherFromZipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherFromZipService {

    private final GeoNamesService geoNamesService;
    private final OpenMeteoService openMeteoService;
    private final WeatherCacheContext cacheContext;
    private final WeatherFromZipResponseAssembler assembler;

    public WeatherFromZipResponse getWeatherFromZip(WeatherFromZipRequest request) {
        System.out.println("Processing request for zip code: " + request.getZipCode());

        var coordinates = geoNamesService.getCoordinatesFromZipCode(request.getZipCode());

        System.out.println("Using coordinates to retrieve weather information...");

        var weatherForecast = openMeteoService.getWeatherForecastFromCoordinates(coordinates);

        System.out.println("Weather Forecast results: " + weatherForecast);

        return assembler.assembleResponse(
            request,
            weatherForecast,
            cacheContext.isFromCache());
    }

}