package com.gorski.weather_are_you_app.services;

import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.clients.GeoNamesClient;
import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.mappers.CoordinatesMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GeoNamesService {

    // Exceptions to invalid zip code or no results
    // Exceptions handling for client not responding
    // @Cacheable for zip code

    private GeoNamesClient geoNamesClient;

    private CoordinatesMapper mapper;

    public CoordinatesDTO getCoordinatesFromZipCode(String zipCode) {
        var response = geoNamesClient.getCoordinatesFromZipCode(zipCode);

        var bestResultCoordinates = response.getPostalCodes().stream().findFirst().orElseThrow();

        return mapper.geoNamesCoordinatesToCoordinatesDTO(bestResultCoordinates);
    }
}
