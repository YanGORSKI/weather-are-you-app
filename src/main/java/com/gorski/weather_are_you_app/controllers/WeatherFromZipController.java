package com.gorski.weather_are_you_app.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gorski.weather_are_you_app.exceptions.InvalidRequestException;
import com.gorski.weather_are_you_app.requests.WeatherFromZipRequest;
import com.gorski.weather_are_you_app.responses.WeatherFromZipResponse;
import com.gorski.weather_are_you_app.services.WeatherFromZipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
public class WeatherFromZipController {

    private final WeatherFromZipService service;

    @PostMapping("/zip")
    public WeatherFromZipResponse getWeatherFromZipCode(
            @RequestBody WeatherFromZipRequest request
        ) throws Exception {
        
        if (request == null || request.getZipCode() == null || request.getZipCode().isBlank()) {
            throw new InvalidRequestException("Zip code must be provided");
        }

        return service.getWeatherFromZip(request);
    }
}