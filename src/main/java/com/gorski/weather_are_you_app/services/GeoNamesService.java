package com.gorski.weather_are_you_app.services;

import org.springframework.cache.annotation.Cacheable;
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

    private GeoNamesClient geoNamesClient;

    private CoordinatesMapper mapper;

    @Cacheable(value = "coordinatesCache", key = "#zipCode")
    public CoordinatesDTO getCoordinatesFromZipCode(String zipCode) {
        
        System.out.println("Fetching coordinates from zip code: " + zipCode);
        var response = geoNamesClient.getCoordinatesFromZipCode(zipCode);

        var bestResultCoordinates = response.getPostalCodes().stream().findFirst().orElseThrow();

        return mapper.geoNamesCoordinatesToCoordinatesDTO(bestResultCoordinates);
    }
}
