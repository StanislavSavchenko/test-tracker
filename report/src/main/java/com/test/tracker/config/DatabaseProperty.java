package com.test.tracker.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseProperty {

    private String driverClassName;
    private String dbUrl;
    private String username;
    private String password;
    private int maximumPoolSize;
}
