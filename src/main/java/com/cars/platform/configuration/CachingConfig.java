package com.cars.platform.configuration;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Context configuration to add Caches to the context platform.
 * Is pretended to add caches in order to avoid repeated loading requests, over-load and DB loads.
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager()
    {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("tokens"),
                new ConcurrentMapCache("users"),
                new ConcurrentMapCache("cars"),
                new ConcurrentMapCache("drivers")));
        return cacheManager;
    }
}
