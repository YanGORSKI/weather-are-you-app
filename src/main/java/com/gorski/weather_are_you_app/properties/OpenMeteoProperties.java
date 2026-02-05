package com.gorski.weather_are_you_app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "openmeteo")
@Data
public class OpenMeteoProperties {
    private String baseUrl;
}
