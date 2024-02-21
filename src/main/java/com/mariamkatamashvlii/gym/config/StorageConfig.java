package com.mariamkatamashvlii.gym.config;

import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.service.TraineeService;
import com.mariamkatamashvlii.gym.service.TrainerService;
import com.mariamkatamashvlii.gym.service.TrainingService;
import com.mariamkatamashvlii.gym.serviceImpl.TraineeServiceImpl;
import com.mariamkatamashvlii.gym.serviceImpl.TrainerServiceImpl;
import com.mariamkatamashvlii.gym.serviceImpl.TrainingServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageConfig {

    @Bean
    public Map<Long, Trainee> trainees() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Trainer> trainers() {
        return new HashMap<>();
    }

    @Bean
    public Map<String, Training> trainings() {
        return new HashMap<>();
    }

    @Bean
    @Primary
    public TraineeService traineeService() {
        return new TraineeServiceImpl();
    }

    @Bean
    @Primary
    public TrainerService trainerService() {
        return new TrainerServiceImpl();
    }

    @Bean
    @Primary
    public TrainingService trainingService() {
        return new TrainingServiceImpl();
    }
}
