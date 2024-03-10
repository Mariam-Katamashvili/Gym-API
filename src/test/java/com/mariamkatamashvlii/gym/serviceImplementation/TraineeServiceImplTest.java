//package com.mariamkatamashvlii.gym.serviceImplementation;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.mariamkatamashvlii.gym.entity.*;
//import com.mariamkatamashvlii.gym.repository.TraineeRepository;
//import com.mariamkatamashvlii.gym.repository.TrainerRepository;
//import com.mariamkatamashvlii.gym.repository.UserRepository;
//import com.mariamkatamashvlii.gym.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.sql.Date;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//
//class TraineeServiceImplTest {
//
//    @Mock
//    private TraineeRepository traineeRepo;
//    @Mock
//    private UserRepository userRepo;
//    @Mock
//    private UserService userService;
//    @Mock
//    private TrainerRepository trainerRepo;
//
//    @InjectMocks
//    private TraineeServiceImpl traineeService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreate() {
//        Trainee trainee = new Trainee();
//        User user = new User();
//        user.setUserId(1L);
//        trainee.setUser(user);
//        trainee.setBirthday(new Date(System.currentTimeMillis()));
//        trainee.setAddress("123 Main St");
//
//        when(userRepo.select(user.getUserId())).thenReturn(user);
//        doNothing().when(traineeRepo).create(trainee);
//
//        traineeService.create(trainee);
//
//        verify(traineeRepo).create(trainee);
//    }
//
//    @Test
//    void testUpdate() {
//        Trainee trainee = new Trainee();
//        User user = new User();
//        user.setUserId(1L);
//        user.setUsername("testUser");
//        user.setPassword("testPass");
//        trainee.setUser(user);
//        trainee.setBirthday(new Date(System.currentTimeMillis()));
//        trainee.setAddress("123 Main St");
//
//        when(userRepo.select(user.getUserId())).thenReturn(user);
//        when(userService.checkCredentials(user.getUsername(), user.getPassword())).thenReturn(true);
//        doNothing().when(traineeRepo).update(trainee);
//
//        traineeService.update(trainee);
//
//        verify(traineeRepo).update(trainee);
//    }
//
//    @Test
//    void testDeleteById() {
//        long id = 1L;
//
//        doNothing().when(traineeRepo).delete(id);
//
//        traineeService.delete(id);
//
//        verify(traineeRepo).delete(id);
//    }
//
//    @Test
//    void testDeleteByUsernameAndPassword() {
//        String username = "testUser";
//        String password = "testPass";
//        Trainee trainee = new Trainee();
//        User user = new User();
//        user.setUsername(username);
//        trainee.setUser(user);
//        trainee.setTrainings(new HashSet<>());
//
//        when(traineeRepo.select(username)).thenReturn(trainee);
//        when(userService.checkCredentials(username, password)).thenReturn(true);
//        doNothing().when(traineeRepo).delete(username);
//
//        traineeService.delete(username, password);
//
//        verify(traineeRepo).delete(username);
//    }
//
//    @Test
//    void testSelectById() {
//        long id = 1L;
//        Trainee expectedTrainee = new Trainee();
//
//        when(traineeRepo.select(id)).thenReturn(expectedTrainee);
//
//        Trainee actualTrainee = traineeService.select(id);
//
//        assertEquals(expectedTrainee, actualTrainee);
//        verify(traineeRepo).select(id);
//    }
//    @Test
//    void testSelectByUsernameAndPassword() {
//        String username = "validUser";
//        String password = "validPass";
//        Trainee expectedTrainee = new Trainee();
//
//        when(userService.checkCredentials(username, password)).thenReturn(true);
//        when(traineeRepo.select(username)).thenReturn(expectedTrainee);
//
//        Trainee actualTrainee = traineeService.select(username, password);
//
//        assertEquals(expectedTrainee, actualTrainee);
//        verify(userService).checkCredentials(username, password);
//        verify(traineeRepo).select(username);
//    }
//
//    @Test
//    void testCheckCredentials() {
//        String username = "user";
//        String password = "pass";
//
//        when(userService.checkCredentials(username, password)).thenReturn(true);
//
//        boolean result = traineeService.checkCredentials(username, password);
//
//        assertTrue(result);
//        verify(userService).checkCredentials(username, password);
//    }
//
//    @Test
//    void testChangePassword() {
//        String username = "user";
//        String currentPassword = "pass";
//        String newPassword = "newPass";
//
//        when(userService.changePassword(username, currentPassword, newPassword)).thenReturn(true);
//
//        boolean result = traineeService.changePassword(username, currentPassword, newPassword);
//
//        assertTrue(result);
//        verify(userService).changePassword(username, currentPassword, newPassword);
//    }
//
//    @Test
//    void testToggleActivation() {
//        String username = "user";
//        String password = "pass";
//        boolean isActive = true;
//
//        when(userService.checkCredentials(username, password)).thenReturn(true);
//
//        traineeService.toggleActivation(username, password, isActive);
//
//        verify(userService).toggleActivation(username, isActive);
//    }
//
//    @Test
//    void testFindAll() {
//        List<Trainee> expectedTrainees = Collections.emptyList();
//
//        when(traineeRepo.findAll()).thenReturn(expectedTrainees);
//
//        List<Trainee> result = traineeService.findAll();
//
//        assertEquals(expectedTrainees, result);
//        verify(traineeRepo).findAll();
//    }
//
//    @Test
//    void testCreateTraineeProfile() {
//        Trainee trainee = new Trainee();
//        User user = new User();
//        user.setUserId(1L);
//        trainee.setUser(user);
//        trainee.setBirthday(new Date(System.currentTimeMillis()));
//        trainee.setAddress("123 Main St");
//
//        when(userRepo.select(user.getUserId())).thenReturn(user);
//        doNothing().when(traineeRepo).create(trainee);
//
//        traineeService.create(trainee);
//
//        verify(traineeRepo).create(trainee);
//    }
////    @Test
////    void testGeTrainingList() {
////        String username = "validUser";
////        String password = "validPass";
////        Date fromDate = Date.valueOf("2023-01-01");
////        Date toDate = Date.valueOf("2023-01-31");
////        String trainerName = "TrainerName";
////        TrainingType trainingType = new TrainingType("Type1");
////        Training training = new Training();
////        training.setTrainingDate(Date.valueOf("2023-01-15"));
////        Trainer trainer = new Trainer();
////        User trainerUser = new User();
////        trainerUser.setFirstName(trainerName);
////        trainer.setUser(trainerUser);
////        training.setTrainer(trainer);
////        training.setTrainingType(trainingType);
////        Trainee trainee = new Trainee();
////        trainee.setTrainings(new HashSet<>(Arrays.asList(training)));
////
////        when(userService.checkCredentials(username, password)).thenReturn(true);
////        when(traineeRepo.select(username)).thenReturn(trainee);
////
////        List<Training> trainingList = traineeService.getTrainings(username, password, fromDate, toDate, trainerName, trainingType);
////
////        assertFalse(trainingList.isEmpty());
////        assertEquals(1, trainingList.size());
////        verify(userService).checkCredentials(username, password);
////    }
//    @Test
//    void testGetNotAssignedTrainerList() {
//        String username = "user";
//        String password = "pass";
//        Trainee trainee = new Trainee();
//        List<Trainer> allTrainers = Collections.emptyList();
//
//        when(traineeRepo.select(username)).thenReturn(trainee);
//        when(trainerRepo.findAll()).thenReturn(allTrainers);
//
//        List<Trainer> result = traineeService.getNotAssignedTrainers(username);
//
//        assertNotNull(result);
//        verify(trainerRepo).findAll();
//    }
//
//}
