package com.mariamkatamashvlii.gym.metrics;

import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraineeCounterMetricsConfig {
    public TraineeCounterMetricsConfig(MeterRegistry meterRegistry, TraineeRepository traineeRepository) {
        Gauge.builder("trainees.count", traineeRepository, TraineeRepository::count)
                .description("Number of Trainees")
                .register(meterRegistry);
    }
}