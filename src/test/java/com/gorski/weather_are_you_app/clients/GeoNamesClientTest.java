package com.gorski.weather_are_you_app.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gorski.weather_are_you_app.responses.GeoNamesResponse;
import com.gorski.weather_are_you_app.responses.GeoNamesResponse.PostalCode;

@SpringBootTest
@ActiveProfiles("test")
class GeoNamesClientTest {

    @Autowired
    private GeoNamesClient geoNamesClient;

    private static final String VALID_ZIPCODE = "14800000";
    private static final Double EXPECTED_LATITUDE = -21.8276284030418;
    private static final Double EXPECTED_LONGITUDE = -48.2011118365019;

    @Test
    void should_return_real_coordinates_when_given_valid_zipcode() {
        // given
        String validZipCode = VALID_ZIPCODE;

        // when
        GeoNamesResponse response = geoNamesClient.getCoordinatesFromZipCode(validZipCode);
        PostalCode realCoordinates = response.getPostalCodes().get(0);

        // then
        assertNotNull(response);
        assertEquals(realCoordinates.getLatitude(), EXPECTED_LATITUDE);
        assertEquals(realCoordinates.getLongitude(), EXPECTED_LONGITUDE);
    }
}