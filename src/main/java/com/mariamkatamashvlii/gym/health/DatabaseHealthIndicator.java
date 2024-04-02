package com.mariamkatamashvlii.gym.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    private final DataSource dataSource;
    private static final String DATABASE = "database";

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1)) {
                return Health.up().withDetail(DATABASE, "Active and reachable").build();
            } else {
                return Health.down().withDetail(DATABASE, "Not reachable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail(DATABASE, "Error when checking database connectivity").build();
        }
    }
}