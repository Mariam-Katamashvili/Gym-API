package com.mariamkatamashvili.gym.health;

import com.mariamkatamashvili.gym.generator.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class PasswordGeneratorHealthIndicator implements HealthIndicator {
    private final PasswordGenerator passwordGenerator;

    @Override
    public Health health() {
        try {
            String password = passwordGenerator.generatePassword();
            if (password.isEmpty()) {
                return Health.down().withDetail("error", "Generation failure").build();
            }
            return Health.up().build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}