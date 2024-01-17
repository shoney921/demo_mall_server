package com.example.demo_mall.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.FlywayExecutor;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {


    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return new FlywayMigrationStrategy() {
            @Override
            public void migrate(Flyway flyway) {
                flyway.migrate();
            }
        };
    }

}
