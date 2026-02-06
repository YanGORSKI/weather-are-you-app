package com.gorski.weather_are_you_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WeatherAreYouAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAreYouAppApplication.class, args);
	}

}
