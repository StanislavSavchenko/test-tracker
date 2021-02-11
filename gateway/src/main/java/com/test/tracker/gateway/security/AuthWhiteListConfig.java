package com.test.tracker.gateway.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthWhiteListConfig {

    @Bean
    @Qualifier("authWhiteList")
    public String[] getAuthWhiteList() {
        return new String[]{
                "/v2/api-docs",
                "/swagger-resources",
                "/documentation/swagger-ui.html",
                "/swagger-resources/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/login",
                "/*/v2/api-docs",
                "/*/swagger-resources",
                "/*/documentation/swagger-ui.html",
                "/*/swagger-resources/**",
                "/*/swagger-resources/configuration/ui",
                "/*/swagger-resources/configuration/security",
                "/*/swagger-ui.html",
                "/*/webjars/**",

        };
    }

}
