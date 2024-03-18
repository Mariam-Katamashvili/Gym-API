//package com.mariamkatamashvlii.gym.serviceImplementation;
//
//import com.mariamkatamashvlii.gym.auth.Validator;
//import com.mariamkatamashvlii.gym.dto.TrainingDTO;
//import com.mariamkatamashvlii.gym.entity.User;
//import com.mariamkatamashvlii.gym.repository.TrainerRepository;
//import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
//import com.mariamkatamashvlii.gym.repository.UserRepository;
//import com.mariamkatamashvlii.gym.service.implementation.TrainerServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class TrainerServiceImplTest {
//    @Mock
//    private TrainerRepository trainerRepo;
//    @Mock
//    private UserRepository userRepo;
//    @Mock
//    private TrainingTypeRepository trainingTypeRepo;
//    @Mock
//    private Validator validation;
//    @Mock
//    private TrainingDTO trainingDTO;
//    @InjectMocks
//    private TrainerServiceImpl trainerService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void testActivateTrainer() {
//        String username = "activeUser";
//        User user = new User();
//        user.setUsername(username);
//        user.setIsActive(false);
//
//        when(userRepo.select(username)).thenReturn(user);
//
//        trainerService.activateTrainer(username, true);
//
//        assertTrue(user.getIsActive());
//        verify(userRepo).update(user);
//    }
//
//    @Test
//    void testDeactivateTrainer() {
//        String username = "inactiveUser";
//        User user = new User();
//        user.setUsername(username);
//        user.setIsActive(true);
//
//        when(userRepo.select(username)).thenReturn(user);
//
//        trainerService.deactivateTrainer(username, false);
//
//        assertFalse(user.getIsActive());
//        verify(userRepo).update(user);
//    }
//
//}
