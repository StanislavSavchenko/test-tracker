package com.test.tracker.config;

import com.test.tracker.model.dto.HibernateProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonPersistenceConfig {
    @Bean
    public HibernateProperties hibernateProperties() {
        return HibernateProperties.builder()
                .dialect("org.hibernate.dialect.PostgreSQL9Dialect")
                .showSql(false)
                .formatSql(true)
                .build();
    }
}
