package com.gorski.weather_are_you_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/weather")
public class WeatherFromZipController {

    @PostMapping()
    public String getWeatherFromZip(@RequestParam("zip") String zip, @RequestParam("additionalInfo") Boolean additionalInfo) {
        return "";
    }
}