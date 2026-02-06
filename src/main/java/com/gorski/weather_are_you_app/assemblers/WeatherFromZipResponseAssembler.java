package com.gorski.weather_are_you_app.assemblers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gorski.weather_are_you_app.requests.WeatherFromZipRequest;
import com.gorski.weather_are_you_app.responses.OpenMeteoResponse;
import com.gorski.weather_are_you_app.responses.WeatherFromZipResponse;

@Component
public class WeatherFromZipResponseAssembler {
    public WeatherFromZipResponse assembleResponse(
            WeatherFromZipRequest request,
            OpenMeteoResponse weatherForecast,
            boolean fromCache) {

        var includeAdditionalInfo = request.getAdditionalInfo() != null && request.getAdditionalInfo();
        var extendedForecast = request.getExtendedForecast() != null && request.getExtendedForecast();

        List<WeatherFromZipResponse.ForecastDay> forecastDays = new ArrayList<>();

        var daily = weatherForecast.getDaily();
        var current = weatherForecast.getCurrent();

        for (int i = 0; i < daily.getTime().size(); i++) {
            var isFirstDay = i == 0;

            forecastDays
                .add(
                    WeatherFromZipResponse.ForecastDay.builder()
                        .date(daily.getTime().get(i))
                        
                        .currentTemperature(
                            isFirstDay ? current.getTemperature() : null
                        )

                        .minTemperature(
                            includeAdditionalInfo || extendedForecast
                            ? daily.getTemperatureMin().get(i)
                            : null
                        )

                        .maxTemperature(
                            includeAdditionalInfo || extendedForecast
                            ? daily.getTemperatureMax().get(i)
                            : null
                        )

                    .build()
                );
                
            if (!extendedForecast) {
                break;
            }
        }

        return WeatherFromZipResponse.builder()
                .weatherForecast(forecastDays)
                .fromCache(fromCache)
                .build();
    }
}