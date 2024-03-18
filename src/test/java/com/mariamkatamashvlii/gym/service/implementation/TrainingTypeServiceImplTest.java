package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
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

}
