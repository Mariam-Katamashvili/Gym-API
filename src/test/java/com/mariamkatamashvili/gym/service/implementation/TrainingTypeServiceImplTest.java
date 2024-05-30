package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvili.gym.entity.TrainingType;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {
    private static final Long ID = 1L;
    private static final String TRAINING_TYPE_NAME = "Yoga";
    private static final String ERROR_MESSAGE = "Error retrieving all training types: ";
    private static final String DATABASE_ERROR = "Database error";

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @Test
    void testFindAll_WhenTrainingTypesExist_ThenReturnTrainingTypeDTOList() {
        // given
        TrainingType trainingType1 = TrainingType.builder().id(ID).trainingTypeName(TRAINING_TYPE_NAME).build();
        TrainingType trainingType2 = TrainingType.builder().id(ID + 1).trainingTypeName("Pilates").build();
        when(trainingTypeRepository.findAll()).thenReturn(List.of(trainingType1, trainingType2));

        // when
        List<TrainingTypeDTO> dtos = trainingTypeService.findAll();

        // then
        assertNotNull(dtos);
        assertFalse(dtos.isEmpty());
        Assertions.assertEquals(2, dtos.size());

        verify(trainingTypeRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_WhenExceptionThrown_ThenThrowGymException() {
        // given
        RuntimeException runtimeException = new RuntimeException(DATABASE_ERROR);
        when(trainingTypeRepository.findAll()).thenThrow(runtimeException);

        // when
        GymException exception = assertThrows(
                GymException.class,
                () -> trainingTypeService.findAll()
        );

        // then
        assertNotNull(exception);
        Assertions.assertEquals(ERROR_MESSAGE + DATABASE_ERROR, exception.getMessage());

        verify(trainingTypeRepository, times(1)).findAll();
    }
}