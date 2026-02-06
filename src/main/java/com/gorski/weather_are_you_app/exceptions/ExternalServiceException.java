package com.gorski.weather_are_you_app.exceptions;

public class ExternalServiceException extends RuntimeException {
public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
