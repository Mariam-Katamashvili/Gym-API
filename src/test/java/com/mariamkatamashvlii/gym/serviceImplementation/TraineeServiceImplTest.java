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
    void testCreate() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(new Date(System.currentTimeMillis()));
        trainee.setAddress("123 Main St");

        when(userRepo.select(user.getId())).thenReturn(user);
        when(traineeRepo.create(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeService.create(trainee);

        assertNotNull(createdTrainee);
        verify(validation).validateTrainee(any(Trainee.class), any(User.class));
        verify(traineeRepo).create(trainee);
    }

    @Test
    void testUpdate() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(new Date(System.currentTimeMillis()));
        trainee.setAddress("123 Main St");

        when(userRepo.select(user.getId())).thenReturn(user);
        when(traineeRepo.update(any(Trainee.class))).thenReturn(trainee);

        Trainee updatedTrainee = traineeService.update(trainee);

        assertNotNull(updatedTrainee);
        verify(traineeRepo).update(trainee);
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
    void testSelect() {
        String username = "testUser";
        Trainee expectedTrainee = new Trainee();

        when(traineeRepo.select(username)).thenReturn(expectedTrainee);

        Trainee actualTrainee = traineeService.select(username);

        assertEquals(expectedTrainee, actualTrainee);
        verify(traineeRepo).select(username);
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

    @Test
    void testChangePassword() {
        String username = "user";
        String currentPassword = "pass";
        String newPassword = "newPass";
        User user = new User();
        user.setUsername(username);
        user.setPassword(currentPassword);

        Trainee trainee = new Trainee();
        trainee.setUser(user);

        when(userRepo.select(username)).thenReturn(user);
        when(traineeRepo.select(username)).thenReturn(trainee);

        boolean result = traineeService.changePassword(username, currentPassword, newPassword);

        assertTrue(result);
        verify(userRepo).update(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void testFindAll() {
        List<Trainee> expectedTrainees = Collections.emptyList();

        when(traineeRepo.findAll()).thenReturn(expectedTrainees);

        List<Trainee> result = traineeService.findAll();

        assertEquals(expectedTrainees, result);
        verify(traineeRepo).findAll();
    }

    @Test
    void testCreateTraineeProfile() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(new Date(System.currentTimeMillis()));
        trainee.setAddress("123 Main St");

        when(userRepo.select(user.getId())).thenReturn(user);
        when(traineeRepo.create(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeService.create(trainee);

        assertNotNull(createdTrainee, "The created trainee should not be null.");
        verify(traineeRepo).create(trainee);
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
