package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.exception.GymException;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepo;

    @Override
    public List<TrainingTypeDTO> findAll() {
        try {
            List<TrainingTypeDTO> result = trainingTypeRepo.findAll().stream()
                    .map(trainingType -> new TrainingTypeDTO(
                            trainingType.getId(),
                            trainingType.getTrainingTypeName()))
                    .toList();
            return result;
        } catch (Exception e) {
            throw new GymException("Error retrieving all training types" + e.getMessage());
        }
    }
}