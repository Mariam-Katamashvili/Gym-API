package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
import com.mariamkatamashvlii.gym.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private TraineeRepository traineeRepo;
    @Mock
    private TrainerRepository trainerRepo;
    @Mock
    private TrainingRepository trainingRepo;
    @Mock
    private TrainingTypeRepository trainingTypeRepo;
    @Mock
    private UsernameGenerator usernameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private Validator validator;
    @Mock
    private JwtTokenGenerator jwtTokenGenerator;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final LocalDate BIRTHDAY = LocalDate.of(2000, 1, 1);
    private static final String ADDRESS = "123 Street";
    private static final boolean ACTIVE_STATUS = true;
    private static final String USERNAME = "John.Doe";
    private static final String PASSWORD = "12345678";
    private static final String NEW_FIRST_NAME = "Jane";
    private static final String NEW_LAST_NAME = "Smith";
    private static final LocalDate NEW_BIRTHDAY = LocalDate.of(2010, 5, 15);
    private static final String NEW_ADDRESS = "456 Street";
    private static final boolean NEW_ACTIVE_STATUS = false;
    private static final LocalDate START_DATE = LocalDate.of(2023, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, 1, 31);
    private static final String TRAINING_NAME = "Morning Yoga";
    private static final Integer DURATION = 60;
    private static final String TRAINER_USERNAME = "Trainer.Username";
    private static final String TRAINING_TYPE_NAME = "Gymnastics";
    private static final Long TRAINING_TYPE_ID = 1L;
    private static final String TRAINER_USERNAME1 = "Trainer.One";
    private static final String TRAINER_USERNAME2 = "Trainer.Two";
    private static final String TRAINER_FIRST_NAME1 = "Trainer";
    private static final String TRAINER_LAST_NAME1 = "One";
    private static final String TRAINER_FIRST_NAME2 = "Trainer";
    private static final String TRAINER_LAST_NAME2 = "Two";
    private static final String TEST_TOKEN = "Token";

    @Test
    void register_success() {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO(FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS);
        User dummyUser = new User();
        dummyUser.setUsername(USERNAME);
        dummyUser.setPassword(PASSWORD);

        when(usernameGenerator.generateUsername(FIRST_NAME, LAST_NAME)).thenReturn(USERNAME);
        when(passwordGenerator.generatePassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(dummyUser, null));
        when(jwtTokenGenerator.generateJwtToken(any(Authentication.class))).thenReturn(TEST_TOKEN);

        // When
        RegistrationResponseDTO actualResult = traineeService.register(registrationRequestDTO);

        // Then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(USERNAME, actualResult.getUsername());
        Assertions.assertEquals(PASSWORD, actualResult.getPassword());
        Assertions.assertEquals(TEST_TOKEN, actualResult.getToken());
        verify(traineeRepo, times(1)).save(any(Trainee.class));
    }

    @Test
    void register_failure() {
        // Given
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO(FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS);
        when(usernameGenerator.generateUsername(FIRST_NAME, LAST_NAME)).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(UserNotCreatedException.class, () -> traineeService.register(registrationRequestDTO));
        verify(traineeRepo, never()).save(any(Trainee.class));
    }

    @Test
    void updateProfile_success() {
        // Given
        User user = new User();
        user.setUsername(USERNAME);

        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setIsActive(ACTIVE_STATUS);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setBirthday(BIRTHDAY);
        trainee.setAddress(ADDRESS);

        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        when(traineeRepo.findByUsername(USERNAME)).thenReturn(trainee);

        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setUsername(USERNAME);
        updateRequestDTO.setFirstName(NEW_FIRST_NAME);
        updateRequestDTO.setLastName(NEW_LAST_NAME);
        updateRequestDTO.setBirthday(NEW_BIRTHDAY);
        updateRequestDTO.setAddress(NEW_ADDRESS);
        updateRequestDTO.setIsActive(NEW_ACTIVE_STATUS);

        // When
        UpdateResponseDTO actualResult = traineeService.updateProfile(updateRequestDTO);

        // Then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(USERNAME, actualResult.getUsername());
        Assertions.assertEquals(NEW_FIRST_NAME, actualResult.getFirstName());
        Assertions.assertEquals(NEW_LAST_NAME, actualResult.getLastName());
        Assertions.assertEquals(NEW_BIRTHDAY, actualResult.getBirthday());
        Assertions.assertEquals(NEW_ADDRESS, actualResult.getAddress());
        Assertions.assertFalse(actualResult.getIsActive());

        verify(userRepo, times(1)).save(user);
        verify(traineeRepo, times(1)).save(trainee);
    }

    @Test
    void delete_success() {
        // Given
        String username = USERNAME;
        User user = new User();
        user.setUsername(username);

        Trainee trainee = new Trainee();
        trainee.setUser(user);

        Training training1 = mock(Training.class);
        Training training2 = mock(Training.class);
        Set<Training> trainings = new HashSet<>(Arrays.asList(training1, training2));
        trainee.setTrainings(trainings);

        when(traineeRepo.findByUsername(username)).thenReturn(trainee);
        when(userRepo.findByUsername(username)).thenReturn(user);

        // When
        traineeService.delete(username);

        // Then
        verify(traineeRepo, times(1)).findByUsername(username);
        verify(userRepo, times(1)).findByUsername(username);
        verify(trainingRepo, times(1)).delete(training1);
        verify(trainingRepo, times(1)).delete(training2);
        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void getUnassignedTrainers_success() {
        // Given
        Trainee trainee = mock(Trainee.class);
        Trainer assignedTrainer = new Trainer();
        User userAssigned = new User();
        userAssigned.setUsername("assignedTrainerUsername");
        assignedTrainer.setUser(userAssigned);

        Trainer unassignedTrainer = new Trainer();
        User userUnassigned = new User();
        userUnassigned.setUsername("unassignedTrainerUsername");
        unassignedTrainer.setUser(userUnassigned);
        TrainingType trainingType = TrainingType.builder()
                .id(TRAINING_TYPE_ID)
                .trainingTypeName(TRAINING_TYPE_NAME)
                .build();
        unassignedTrainer.setSpecialization(trainingType);

        when(traineeRepo.findByUsername(USERNAME)).thenReturn(trainee);
        when(trainee.getTrainers()).thenReturn(Collections.singletonList(assignedTrainer));
        when(trainerRepo.findAll()).thenReturn(Arrays.asList(assignedTrainer, unassignedTrainer));
        doNothing().when(validator).validateTraineeExists(USERNAME);

        // When
        List<TrainerDTO> actualResult = traineeService.getUnassignedTrainers(USERNAME);

        // Then
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals("unassignedTrainerUsername", actualResult.get(0).getUsername());
        verify(validator, times(1)).validateTraineeExists(USERNAME);
        verify(traineeRepo, times(1)).findByUsername(USERNAME);
        verify(trainerRepo, times(1)).findAll();
    }

    @Test
    void getTrainings_success() {
        // Given
        Training training = new Training();
        training.setTrainingName(TRAINING_NAME);
        training.setTrainingDate(LocalDate.of(2023, 1, 15));
        training.setDuration(DURATION);
        Trainer trainer = new Trainer();
        User trainerProfile = new User();
        trainerProfile.setUsername(TRAINER_USERNAME);
        trainer.setUser(trainerProfile);
        training.setTrainer(trainer);
        TrainingType trainingType = new TrainingType();
        trainingType.setId(TRAINING_TYPE_ID);
        trainingType.setTrainingTypeName(TRAINING_TYPE_NAME);
        training.setTrainingType(trainingType);

        when(trainingRepo.findTraineeTrainingsByCriteria(USERNAME, START_DATE, END_DATE, null, TRAINING_TYPE_NAME))
                .thenReturn(List.of(training));
        when(trainingTypeRepo.findByTrainingTypeName(TRAINING_TYPE_NAME))
                .thenReturn(Optional.of(trainingType));

        TrainingsRequestDTO request = new TrainingsRequestDTO(USERNAME, START_DATE, END_DATE, null, new TrainingTypeDTO(TRAINING_TYPE_ID, TRAINING_TYPE_NAME));

        // When
        List<TrainingResponseDTO> actualTrainings = traineeService.getTrainings(request);

        // Then
        Assertions.assertEquals(1, actualTrainings.size());
        TrainingResponseDTO responseDTO = actualTrainings.get(0);
        Assertions.assertEquals(TRAINING_NAME, responseDTO.getTrainingName());
        Assertions.assertEquals(LocalDate.of(2023, 1, 15), responseDTO.getDate());
        Assertions.assertEquals(DURATION, responseDTO.getDuration());
        Assertions.assertEquals(TRAINER_USERNAME, responseDTO.getName());
        Assertions.assertEquals(TRAINING_TYPE_NAME, responseDTO.getTrainingType().getTrainingTypeName());
        Assertions.assertEquals(TRAINING_TYPE_ID, responseDTO.getTrainingType().getTrainingTypeId());

        verify(trainingRepo).findTraineeTrainingsByCriteria(USERNAME, START_DATE, END_DATE, null, TRAINING_TYPE_NAME);
        verify(trainingTypeRepo).findByTrainingTypeName(TRAINING_TYPE_NAME);
        verify(validator).validateTraineeExists(USERNAME);
    }


    @Test
    void updateTrainers_success() {
        // Given
        User trainerProfile1 = new User();
        trainerProfile1.setUsername(TRAINER_USERNAME1);
        trainerProfile1.setFirstName(TRAINER_FIRST_NAME1);
        trainerProfile1.setLastName(TRAINER_LAST_NAME1);

        User trainerProfileUser2 = new User();
        trainerProfileUser2.setUsername(TRAINER_USERNAME2);
        trainerProfileUser2.setFirstName(TRAINER_FIRST_NAME2);
        trainerProfileUser2.setLastName(TRAINER_LAST_NAME2);

        TrainingType trainingType = TrainingType.builder()
                .id(TRAINING_TYPE_ID)
                .trainingTypeName(TRAINING_TYPE_NAME)
                .build();

        Trainer trainer1 = new Trainer();
        trainer1.setUser(trainerProfile1);
        trainer1.setSpecialization(trainingType);

        Trainer trainer2 = new Trainer();
        trainer2.setUser(trainerProfileUser2);
        trainer2.setSpecialization(trainingType);

        User traineeProfile = User.builder()
                .username(USERNAME)
                .build();

        Trainee trainee = Trainee.builder()
                .user(traineeProfile)
                .build();
        traineeProfile.setTrainee(trainee);
        UpdateTrainersRequestDTO updateTrainersRequestDTO = new UpdateTrainersRequestDTO(USERNAME, List.of(new TrainerUsenameDTO(TRAINER_USERNAME1), new TrainerUsenameDTO(TRAINER_USERNAME2)));

        when(trainerRepo.findByUsername(TRAINER_USERNAME1)).thenReturn(trainer1);
        when(trainerRepo.findByUsername(TRAINER_USERNAME2)).thenReturn(trainer2);
        when(traineeRepo.findByUsername(USERNAME)).thenReturn(trainee);

        // When
        List<TrainerDTO> actualUpdatedTrainers = traineeService.updateTrainers(updateTrainersRequestDTO);

        // Then
        Assertions.assertNotNull(actualUpdatedTrainers);
        Assertions.assertEquals(2, actualUpdatedTrainers.size());
        Assertions.assertTrue(actualUpdatedTrainers.stream().anyMatch(t -> TRAINER_USERNAME1.equals(t.getUsername())));
        Assertions.assertTrue(actualUpdatedTrainers.stream().anyMatch(t -> TRAINER_USERNAME2.equals(t.getUsername())));

        verify(validator).validateTraineeExists(USERNAME);
        verify(traineeRepo).findByUsername(USERNAME);
        verify(trainerRepo).findByUsername(TRAINER_USERNAME1);
        verify(trainerRepo).findByUsername(TRAINER_USERNAME2);
        verify(traineeRepo).save(trainee);
    }

    @Test
    void toggleActivation_success() {
        // Given
        String username = USERNAME;
        boolean newIsActiveStatus = NEW_ACTIVE_STATUS;
        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO(username, newIsActiveStatus);

        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setIsActive(!newIsActiveStatus);

        when(userRepo.findByUsername(username)).thenReturn(existingUser);

        // When
        traineeService.toggleActivation(toggleActivationDTO);

        // Then
        verify(userRepo).findByUsername(USERNAME);
        verify(userRepo).save(existingUser);
        verify(validator).validateUserExists(USERNAME);
        Assertions.assertEquals(newIsActiveStatus, existingUser.getIsActive(), "The active status should be updated.");

    }
}