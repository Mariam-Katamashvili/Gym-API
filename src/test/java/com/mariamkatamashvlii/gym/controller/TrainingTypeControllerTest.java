package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

//    @Test
//    void getAllTrainingTypes_ShouldReturnTrainingTypes() throws Exception {
//        TrainingTypeDTO trainingType1 = new TrainingTypeDTO(1L, "Yoga");
//        TrainingTypeDTO trainingType2 = new TrainingTypeDTO(2L, "Pilates");
//
//        List<TrainingTypeDTO> allTrainingTypes = Arrays.asList(trainingType1, trainingType2);
//
//        given(trainingTypeService.findAll()).willReturn(allTrainingTypes);
//
//        mockMvc.perform(get("/trainingTypes/getAll")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("[{'trainingTypeId':1,'trainingTypeName':'Yoga'},{'trainingTypeId':2,'trainingTypeName':'Pilates'}]"));
//    }
}
