package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import lombok.Generated;

import java.util.List;

@Generated
public interface TrainingTypeRepository {
    TrainingType select(long id);

    List<TrainingTypeDTO> findAll();
}
