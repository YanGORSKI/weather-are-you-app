package com.gorski.weather_are_you_app.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.gorski.weather_are_you_app.properties.GeoNamesProperties;
import com.gorski.weather_are_you_app.responses.GeoNamesResponse;

@Component
public class GeoNamesClient {

    private final RestClient geoNamesRestClient;
    private final GeoNamesProperties geoNamesProperties;

    public GeoNamesClient(
        @Qualifier("geoNamesRestClient") RestClient geoNamesRestClient,
        GeoNamesProperties geoNamesProperties)
    {
        this.geoNamesRestClient = geoNamesRestClient;
        this.geoNamesProperties = geoNamesProperties;
    }

    public GeoNamesResponse getCoordinatesFromZipCode(String zipCode) {
        System.out.println("Making API call to GeoNames for zip code: " + zipCode);
        
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
