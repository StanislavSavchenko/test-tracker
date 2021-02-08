package com.test.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ReportLauncher {

    public static void main(String[] args) {
        SpringApplication.run(ReportLauncher.class, args);
    }

}
