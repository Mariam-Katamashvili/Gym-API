package com.mariamkatamashvili.gym.health;

import com.mariamkatamashvili.gym.generator.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UsernameGeneratorHealthIndicator implements HealthIndicator {
    private final UsernameGenerator usernameGenerator;

    @Override
    public Health health() {
        try {
            String username = usernameGenerator.generateUsername("Test", "User");
            if (username.isEmpty()) {
                return Health.down().withDetail("error", "Generation failure").build();
            }
            return Health.up().build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}