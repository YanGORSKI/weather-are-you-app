package com.gorski.weather_are_you_app.exceptions;

public class CoordinatesNotFoundException extends RuntimeException {
public CoordinatesNotFoundException(String message) {
        super(message);
    }
}
