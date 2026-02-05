package com.gorski.weather_are_you_app.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OpenMeteoResponse {
    private Current current;
    private Daily daily;

    @Data
    public static class Current {
        @JsonProperty("temperature_2m")
        private Double temperature;
    }

    @Data
    public static class Daily {
        private List<String> time;

        @JsonProperty("temperature_2m_min")
        private List<Double> temperatureMin;

        @JsonProperty("temperature_2m_max")
        private List<Double> temperatureMax;
    }
}
