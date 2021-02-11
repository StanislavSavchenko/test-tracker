package com.test.tracker.flyway.strategy;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MigrationStrategy implements FlywayMigrationStrategy {

    public void migrate(Flyway flyway) {
        log.info("========= flyway migration");
        flyway.migrate();
        log.info("========= flyway migration completed");
    }
}
