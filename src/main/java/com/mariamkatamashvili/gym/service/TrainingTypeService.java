package com.mariamkatamashvili.gym.service;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;

import java.util.List;

public interface TrainingTypeService {
    List<TrainingTypeDTO> findAll();
}