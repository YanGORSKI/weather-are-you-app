package com.gorski.weather_are_you_app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.gorski.weather_are_you_app.properties.GeoNamesProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GeoNamesClientConfig {

    private final GeoNamesProperties geoNamesProperties;

    @Bean
    RestClient geoNamesRestClient() {
        return RestClient.builder()
                .baseUrl(geoNamesProperties.getBaseUrl())
                .build();
    }

}
