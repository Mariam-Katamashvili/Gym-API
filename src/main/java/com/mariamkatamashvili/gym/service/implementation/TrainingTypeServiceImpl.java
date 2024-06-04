package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.service.TrainingTypeService;
import com.mariamkatamashvili.gym.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepo;

    @Override
    public List<TrainingTypeDTO> findAll() {
        try {
            return trainingTypeRepo.findAll().stream()
                    .map(trainingType -> new TrainingTypeDTO(
                            trainingType.getId(),
                            trainingType.getTrainingTypeName()))
                    .toList();
        } catch (Exception e) {
            throw new GymException("Error retrieving all training types: " + e.getMessage());
        }
    }
}