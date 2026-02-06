package com.gorski.weather_are_you_app.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherFromZipResponse {
    private List<ForecastDay> weatherForecast;
    private Boolean fromCache;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ForecastDay {
        private String date;
        private Double currentTemperature;
        private Double minTemperature;
        private Double maxTemperature;
    }

}
