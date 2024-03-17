package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(TrainingTypeController.class)
class TrainingTypeControllerTest {

    @MockBean
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingTypeController).build();
    }

    @Test
    void getAllTrainingTypes_ShouldReturnTrainingTypes() throws Exception {
        TrainingTypeDTO trainingType1 = new TrainingTypeDTO(1L, "Yoga");
        TrainingTypeDTO trainingType2 = new TrainingTypeDTO(2L, "Pilates");

        List<TrainingTypeDTO> allTrainingTypes = Arrays.asList(trainingType1, trainingType2);

        given(trainingTypeService.findAll()).willReturn(allTrainingTypes);

    }
}
