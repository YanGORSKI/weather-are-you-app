package com.gorski.weather_are_you_app.contexts;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class WeatherCacheContext {
     private boolean fromCache = true;

    public boolean isFromCache() {
        return fromCache;
    }

    public void markAsFresh() {
        this.fromCache = false;
    }
}
