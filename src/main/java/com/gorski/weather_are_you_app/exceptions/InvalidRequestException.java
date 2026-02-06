package com.gorski.weather_are_you_app.exceptions;

import org.apache.coyote.BadRequestException;

public class InvalidRequestException extends BadRequestException{
    public InvalidRequestException(String message) {
        super(message);
    }
}
