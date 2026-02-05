package com.gorski.weather_are_you_app.services;

import org.springframework.stereotype.Service;

import com.gorski.weather_are_you_app.clients.GeoNamesClient;
import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.mappers.CoordinatesMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GeoNamesService {

    private GeoNamesClient geoNamesClient;

    private CoordinatesMapper mapper;

    public CoordinatesDTO getCoordinatesFromZipCode(String zipCode) {
        var response = geoNamesClient.getCoordinatesFromZipCode(zipCode);

        var approximateCoordinates = response.getPostalCodes().stream().findFirst().orElseThrow();

        return mapper.geoNamesCoordinatesToCoordinatesDTO(approximateCoordinates);
    }
}
