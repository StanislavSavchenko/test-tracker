package com.test.tracker.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FlywayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlywayApplication.class, args);
    }

}
