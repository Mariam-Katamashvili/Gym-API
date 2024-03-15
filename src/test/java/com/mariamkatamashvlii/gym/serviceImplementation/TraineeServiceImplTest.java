package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.auth.Validation;
import com.mariamkatamashvlii.gym.dto.TrainerDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.serviceImplementation.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TraineeServiceImplTest {
    @Mock
    private TraineeRepository traineeRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private TrainerRepository trainerRepo;
    @Mock
    private Validation validation;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }




    @Test
    void testDelete() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUser(new User());

        when(traineeRepo.select(username)).thenReturn(trainee);

        traineeService.delete(username);

        verify(traineeRepo).delete(username);
    }



    @Test
    void testActivateTrainee() {
        String username = "activeUser";
        User user = new User();
        user.setUsername(username);
        user.setIsActive(false);

        when(userRepo.select(username)).thenReturn(user);

        traineeService.activateTrainee(username, true);

        assertTrue(user.getIsActive());
        verify(userRepo).update(user);
    }

    @Test
    void testDeactivateTrainee() {
        String username = "inactiveUser";
        User user = new User();
        user.setUsername(username);
        user.setIsActive(true);

        when(userRepo.select(username)).thenReturn(user);

        traineeService.deactivateTrainee(username, false);

        assertFalse(user.getIsActive());
        verify(userRepo).update(user);
    }





//    @Test
//    void testGetTrainingList() {
//        String username = "validUser";
//        Date fromDate = Date.valueOf("2023-01-01");
//        Date toDate = Date.valueOf("2023-01-31");
//        String trainerName = "TrainerName";
//        TrainingType trainingType = new TrainingType();
//        trainingType.setTrainingTypeName("Type1");
//
//        Trainer trainer = new Trainer();
//        User trainerUser = new User();
//        trainerUser.setFirstName(trainerName);
//        trainer.setUser(trainerUser);
//
//        Training training = new Training();
//        training.setTrainingDate(Date.valueOf("2023-01-15"));
//        training.setTrainer(trainer);
//        training.setTrainingType(trainingType);
//
//        Trainee trainee = new Trainee();
//        trainee.setUser(new User());
//        trainee.setTrainings(new HashSet<>(List.of(training)));
//
//        when(traineeRepo.select(username)).thenReturn(trainee);
//
//        List<TrainingDTO> trainingList = traineeService.getTrainings(username, fromDate, toDate, trainerName, trainingType);
//
//        assertFalse(trainingList.isEmpty(), "The training list should not be empty.");
//        assertEquals(1, trainingList.size(), "The training list should contain exactly one training.");
//    }

    @Test
    void testGetNotAssignedTrainerList() {
        String username = "user";
        Trainee trainee = new Trainee();
        List<Trainer> allTrainers = Collections.emptyList();

        when(traineeRepo.select(username)).thenReturn(trainee);
        when(trainerRepo.findAll()).thenReturn(allTrainers);

        List<TrainerDTO> result = traineeService.getNotAssignedTrainers(username);

        assertNotNull(result);
        verify(trainerRepo).findAll();
    }

    private final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
}
