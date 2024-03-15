package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.service.serviceImplementation.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepo;
    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


//    @Test
//    void testFindAll() {
//        List<TrainingType> expectedTrainingTypes = Arrays.asList(new TrainingType(), new TrainingType());
//
//        when(trainingTypeRepo.findAll()).thenReturn(expectedTrainingTypes);
//
//        List<TrainingType> actualTrainingTypes = trainingTypeService.findAll();
//
//        assertEquals(expectedTrainingTypes, actualTrainingTypes);
//        verify(trainingTypeRepo).findAll();
//    }
}
