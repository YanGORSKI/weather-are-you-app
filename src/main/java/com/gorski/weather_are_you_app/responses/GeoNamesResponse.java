package com.gorski.weather_are_you_app.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GeoNamesResponse {
    @JsonProperty("postalCodes")
    private List<PostalCode> postalCodes;

    @Data
    public static class PostalCode {
        @JsonProperty("lat")
        private Double latitude;

        @JsonProperty("lng")
        private Double longitude;
    }
}
