package com.cache.bgc.config;

import com.cache.bgc.model.ObjectCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class CacheConfiguration {

    @Bean
    public ObjectCache objectCache() {
        return new ObjectCache();
    }
}
