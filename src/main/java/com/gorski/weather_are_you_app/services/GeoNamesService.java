package com.gorski.weather_are_you_app.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.clients.GeoNamesClient;
import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.exceptions.CoordinatesNotFoundException;
import com.gorski.weather_are_you_app.mappers.CoordinatesMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GeoNamesService {

    private GeoNamesClient geoNamesClient;

    private CoordinatesMapper mapper;

    @Cacheable(value = "coordinatesCache", key = "#zipCode")
    public CoordinatesDTO getCoordinatesFromZipCode(String zipCode) {

        var response = geoNamesClient.getCoordinatesFromZipCode(zipCode);

        var bestResultCoordinates = response.getPostalCodes().stream()
        .findFirst()
        .orElseThrow(() ->
            new CoordinatesNotFoundException(
                "Coordinates not found for zip code: " + zipCode
            )
        );

        return mapper.geoNamesCoordinatesToCoordinatesDTO(bestResultCoordinates);
    }
}
