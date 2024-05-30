package com.mariamkatamashvili.gym.service;

import com.mariamkatamashvili.gym.dto.trainingDto.TrainingRequestDTO;

public interface TrainingService {
    void create(TrainingRequestDTO trainingRequestDTO);

    void removeTrainings(String username);
}