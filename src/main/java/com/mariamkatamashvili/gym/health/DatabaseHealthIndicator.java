package com.mariamkatamashvili.gym.health;

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
    private static final String DATABASE_PARAMETER_NAME = "database";

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1)) {
                return Health.up().withDetail(DATABASE_PARAMETER_NAME, "Active and reachable").build();
            } else {
                return Health.down().withDetail(DATABASE_PARAMETER_NAME, "Not reachable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail(DATABASE_PARAMETER_NAME, "Error when checking database connectivity").build();
        }
    }
}