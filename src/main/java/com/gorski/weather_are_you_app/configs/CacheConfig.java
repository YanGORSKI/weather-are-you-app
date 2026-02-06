package com.gorski.weather_are_you_app.configs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
        .expireAfterWrite(24, TimeUnit.HOURS);
    }

    @Bean
    public CacheManager cacheManager() {
        
        CaffeineCache coordinatesCache = new CaffeineCache(
            "coordinatesCache",
            Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build()
        );

        CaffeineCache weatherCache = new CaffeineCache(
            "weatherCache",
            Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build()
        );

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(coordinatesCache, weatherCache));

        return cacheManager;
    }

}
