package com.gorski.weather_are_you_app.mappers;

import org.springframework.stereotype.Component;

import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.responses.GeoNamesResponse.PostalCode;

@Component
public class CoordinatesMapper {
    public CoordinatesDTO geoNamesCoordinatesToCoordinatesDTO(PostalCode geoNamesCoordinates) {

        return CoordinatesDTO.builder()
                .latitude(geoNamesCoordinates.getLatitude())
                .longitude(geoNamesCoordinates.getLongitude())
                .build();
    }
}
