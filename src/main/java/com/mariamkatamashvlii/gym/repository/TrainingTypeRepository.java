package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;

import java.util.List;

public interface TrainingTypeRepository {
    TrainingType select(long id);

    List<TrainingTypeDTO> findAll();
}
