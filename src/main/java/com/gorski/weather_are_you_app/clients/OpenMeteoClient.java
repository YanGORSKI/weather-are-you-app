package com.gorski.weather_are_you_app.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.exceptions.ExternalServiceException;
import com.gorski.weather_are_you_app.responses.OpenMeteoResponse;

@Component
public class OpenMeteoClient {    
    private final RestClient openMeteoRestClient;

    public OpenMeteoClient(
        @Qualifier("openMeteoRestClient") RestClient openMeteoRestClient
    ) {
        this.openMeteoRestClient = openMeteoRestClient;
    }

    public OpenMeteoResponse getForecastFromCoordinates(CoordinatesDTO coord) {
        System.out.println(". . . . CONNECTING TO OPEN-METEO API . . . .");
        try {
            return openMeteoRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("forecast")
                    .queryParam("latitude", coord.getLatitude())
                    .queryParam("longitude", coord.getLongitude())
                    .queryParam("current", "temperature_2m")
                    .queryParam("daily", "temperature_2m_min,temperature_2m_max")
                    .build())
                .retrieve()
                .body(OpenMeteoResponse.class);
        } catch (Exception ex) {
            throw new ExternalServiceException(
                "Failed to fetch forecast details from external Service",
                ex
            );
        }
    }

}
