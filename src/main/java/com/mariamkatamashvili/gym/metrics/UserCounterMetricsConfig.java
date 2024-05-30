package com.mariamkatamashvili.gym.metrics;

import com.mariamkatamashvili.gym.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCounterMetricsConfig {
    public UserCounterMetricsConfig(MeterRegistry meterRegistry, UserRepository userRepository) {
        Gauge.builder("users.count", userRepository, UserRepository::count)
                .description("Number of users")
                .register(meterRegistry);
    }
}