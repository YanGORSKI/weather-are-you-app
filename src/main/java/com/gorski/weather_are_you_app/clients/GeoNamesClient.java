package com.gorski.weather_are_you_app.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.gorski.weather_are_you_app.properties.GeoNamesProperties;
import com.gorski.weather_are_you_app.responses.GeoNamesResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GeoNamesClient {

    private final GeoNamesProperties geoNamesProperties;

    private final RestClient geoNamesRestClient;

    public GeoNamesResponse getCoordinatesFromZipCode(String zipCode) {
        return geoNamesRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/postalCodeSearchJSON")
                        .queryParam("postalcode", zipCode)
                        .queryParam("username", geoNamesProperties.getUsername())
                        .build())
                .retrieve()
                .body(GeoNamesResponse.class);
    }

}
