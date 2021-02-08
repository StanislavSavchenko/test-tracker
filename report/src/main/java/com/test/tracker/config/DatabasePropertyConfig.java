package com.test.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class DatabasePropertyConfig {

    @Bean
    public DatabaseProperty getDatabaseProperty(Environment env) {
        return DatabaseProperty.builder()
                .driverClassName(env.getProperty("TRACKER_DRIVER_CLASS_NAME"))
                .dbUrl(env.getProperty("TRACKER_DB_URL"))
                .username(env.getProperty("TRACKER_DB_USER"))
                .password(env.getProperty("TRACKER_DB_PASSWORD"))
                .maximumPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("TRACKER_DB_MAXIMUM_POOL_SIZE"))))
                .build();
    }

}
