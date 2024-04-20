package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Generated
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
    Optional<TrainingType> findByTrainingTypeName(String trainingTypeName);
}