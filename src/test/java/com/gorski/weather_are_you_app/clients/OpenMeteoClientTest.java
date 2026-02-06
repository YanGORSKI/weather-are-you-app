package com.gorski.weather_are_you_app.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gorski.weather_are_you_app.dtos.CoordinatesDTO;
import com.gorski.weather_are_you_app.responses.OpenMeteoResponse;

@Tag("integration")
@SpringBootTest
@ActiveProfiles("test")
class OpenMeteoClientTest {

    @Autowired
    private OpenMeteoClient openMeteoClient;

    private static final Double VALID_LATITUDE = -21.8276284030418;
    private static final Double VALID_LONGITUDE = -48.2011118365019;
    private static final int NUMBER_OF_ENTRIES_EXPECTED = 7;

    @Test
    void should_return_successfully_when_given_valid_coordinates() {
        // given
        var validCoordinates = CoordinatesDTO.builder()
                .latitude(VALID_LATITUDE)
                .longitude(VALID_LONGITUDE)
                .build();

        // when
        OpenMeteoResponse response = openMeteoClient.getForecastFromCoordinates(validCoordinates);
        var current = response.getCurrent();
        var daily = response.getDaily();

        // then
        assertNotNull(response);
        assertNotNull(current);
        assertEquals(daily.getTime().size(), NUMBER_OF_ENTRIES_EXPECTED);
        assertEquals(daily.getTemperatureMin().size(), NUMBER_OF_ENTRIES_EXPECTED);
        assertEquals(daily.getTemperatureMax().size(), NUMBER_OF_ENTRIES_EXPECTED);
    }
}