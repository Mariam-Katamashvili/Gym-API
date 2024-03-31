package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.exception.TrainingTypeFetchException;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepo;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    private static final Long TRAINING_TYPE_ID = 1L;
    private static final String TRAINING_TYPE_NAME = "Gymnastics";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void FindAll_Success() {
        // Given
        TrainingType trainingType = new TrainingType(TRAINING_TYPE_ID, TRAINING_TYPE_NAME, Collections.emptySet(), Collections.emptySet());
        given(trainingTypeRepo.findAll()).willReturn(List.of(trainingType));

        // When
        List<TrainingTypeDTO> result = trainingTypeService.findAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTrainingTypeId()).isEqualTo(TRAINING_TYPE_ID);
        assertThat(result.get(0).getTrainingTypeName()).isEqualTo(TRAINING_TYPE_NAME);
    }

    @Test
    void FindAll_ThrowException() {
        // Given
        given(trainingTypeRepo.findAll()).willThrow(new RuntimeException("Database error"));

        // When & Then
        Exception exception = assertThrows(TrainingTypeFetchException.class, () -> trainingTypeService.findAll());
        assertThat(exception.getMessage()).contains("Error retrieving all training types");
    }
}
