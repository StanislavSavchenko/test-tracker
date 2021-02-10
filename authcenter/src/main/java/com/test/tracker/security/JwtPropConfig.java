package com.test.tracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class JwtPropConfig {
    @Bean
    public JwtAuthenticationProperty jwtConfig(Environment env) {
        return JwtAuthenticationProperty.builder()
                .expiration(Integer.parseInt(Objects.requireNonNull(env.getProperty("TRACKER_JWT_EXPIRATION"))))
                .header(Objects.requireNonNull(env.getProperty("TRACKER_JWT_HEADER")))
                .prefix(Objects.requireNonNull(env.getProperty("TRACKER_JWT_PREFIX")))
                .url(Objects.requireNonNull(env.getProperty("TRACKER_JWT_LOGIN_URL")))
                .secret(Objects.requireNonNull(env.getProperty("TRACKER_JWT_SECRET")))
                .build();
    }
}
