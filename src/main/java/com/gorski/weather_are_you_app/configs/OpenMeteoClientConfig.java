package com.gorski.weather_are_you_app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.gorski.weather_are_you_app.properties.OpenMeteoProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenMeteoClientConfig {

    private final OpenMeteoProperties openMeteoProperties;

    @Bean(name = "openMeteoRestClient")
    RestClient openMeteoRestClient() {
        return RestClient.builder()
                .baseUrl(openMeteoProperties.getBaseUrl())
                .build();
    }

}
