//package com.mariamkatamashvlii.gym.service.implementation;
//
//import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
//import com.mariamkatamashvlii.gym.entity.TrainingType;
//import com.mariamkatamashvlii.gym.exception.GymException;
//import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.assertj.core.api.AssertionsForClassTypes.tuple;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class TrainingTypeServiceImplTest {
//    @Mock
//    private TrainingTypeRepository trainingTypeRepo;
//
//    @InjectMocks
//    private TrainingTypeServiceImpl trainingTypeService;
//
//    private static final Long EXPECTED_TRAINING_TYPE_ID = 1L;
//    private static final String EXPECTED_TRAINING_TYPE_NAME = "Gymnastics";
//
//    @Test
//    void whenFindAll_thenReturnTrainingTypes() {
//        // Given
//        TrainingType trainingType = new TrainingType(EXPECTED_TRAINING_TYPE_ID, EXPECTED_TRAINING_TYPE_NAME, Collections.emptySet(), Collections.emptySet());
//        given(trainingTypeRepo.findAll()).willReturn(List.of(trainingType));
//
//        // When
//        List<TrainingTypeDTO> actualResults = trainingTypeService.findAll();
//
//        // Then
//        assertThat(actualResults).hasSize(1)
//                .extracting(TrainingTypeDTO::getTrainingTypeId, TrainingTypeDTO::getTrainingTypeName)
//                .contains(tuple(EXPECTED_TRAINING_TYPE_ID, EXPECTED_TRAINING_TYPE_NAME));
//    }
//
//    @Test
//    void whenFindAll_thenThrowTrainingTypeFetchException() {
//        // Given
//        given(trainingTypeRepo.findAll()).willThrow(new RuntimeException("Database error"));
//
//        // When & Then
//        assertThatThrownBy(() -> trainingTypeService.findAll())
//                .isInstanceOf(GymException.class)
//                .hasMessageContaining("Error retrieving all training types");
//    }
//}