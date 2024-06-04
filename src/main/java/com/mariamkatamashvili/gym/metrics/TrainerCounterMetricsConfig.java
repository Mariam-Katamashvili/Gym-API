package com.mariamkatamashvili.gym.metrics;

import com.mariamkatamashvili.gym.repository.TrainerRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainerCounterMetricsConfig {
    TrainerCounterMetricsConfig(MeterRegistry meterRegistry, TrainerRepository trainerRepository) {
        Gauge.builder("trainers.count", trainerRepository, TrainerRepository::count)
                .description("Number of Trainers")
                .register(meterRegistry);
    }
}