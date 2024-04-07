package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.exception.UserNotFoundException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.JwtTokenUtil;
import com.mariamkatamashvlii.gym.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private TrainerRepository trainerRepo;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private UserRepository userRepo;
    @Mock
    private UsernameGenerator usernameGenerator;
    @Mock
    private Validator validator;
    @Mock
    private User user;
    @Mock
    private Trainer trainer;
    @Mock
    private TrainingTypeDTO trainingTypeDTO;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private TrainerServiceImpl trainerService;

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String USERNAME = "John.Doe";
    private static final String PASSWORD = "12345678";
    private static final Boolean ACTIVE_STATUS = true;
    private static final Long TRAINING_TYPE_ID = 1L;
    private static final String TRAINING_TYPE_NAME = "Gymnastics";
    private static final String UNKNOWN_USERNAME = "Incorrect Username";
    private static final String NEW_FIRST_NAME = "Jane";
    private static final String TRAINEE_USERNAME = "traineeName";
    private static final Boolean NEW_ACTIVE_STATUS = false;
    private static final String USER_NOT_FOUND = "User not found for username: ";
    private static final String TRAINER_NOT_FOUND = "Trainer not found for username: ";
    private static final String TOKEN = "Token";
    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername(USERNAME);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setIsActive(ACTIVE_STATUS);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(TRAINING_TYPE_ID);
        trainingType.setTrainingTypeName(TRAINING_TYPE_NAME);

        trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingTypeId(TRAINING_TYPE_ID);
        trainingTypeDTO.setTrainingTypeName(TRAINING_TYPE_NAME);

        trainer = new Trainer();
        trainer.setSpecialization(trainingType);
        trainer.setUser(user);

        User traineeProfile = new User();
        traineeProfile.setUsername(TRAINEE_USERNAME);
        traineeProfile.setFirstName("TraineeFirstName");
        traineeProfile.setLastName("TraineeLastName");

        Trainee trainee = new Trainee();
        trainee.setUser(traineeProfile);

        trainer.setTrainees(List.of(trainee));

        lenient().when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        lenient().when(trainerRepo.findByUsername(USERNAME)).thenReturn(trainer);
    }

    @Test
    void register_Success() {
        // Given
        TrainingType trainingType = new TrainingType();
        trainingType.setId(TRAINING_TYPE_ID);
        trainingType.setTrainingTypeName(TRAINING_TYPE_NAME);

        User dummyUser = new User();
        dummyUser.setUsername(USERNAME);
        dummyUser.setPassword(PASSWORD);

        when(trainingTypeRepository.findById(TRAINING_TYPE_ID)).thenReturn(Optional.of(trainingType));
        when(usernameGenerator.generateUsername(anyString(), anyString())).thenReturn(USERNAME);
        when(passwordGenerator.generatePassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(dummyUser, null));
        when(jwtTokenUtil.generateJwtToken(any(Authentication.class))).thenReturn(TOKEN);

        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setFirstName(FIRST_NAME);
        registrationRequestDTO.setLastName(LAST_NAME);
        registrationRequestDTO.setSpecialization(new TrainingTypeDTO(TRAINING_TYPE_ID, TRAINING_TYPE_NAME));

        // When
        RegistrationResponseDTO result = trainerService.register(registrationRequestDTO);

        // Then
        Assertions.assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        assertEquals(PASSWORD, result.getPassword());
        assertEquals(TOKEN, result.getToken());
        verify(usernameGenerator).generateUsername(FIRST_NAME, LAST_NAME);
        verify(passwordGenerator).generatePassword();
        verify(trainingTypeRepository).findById(TRAINING_TYPE_ID);
        verify(trainerRepo).save(any(Trainer.class));
    }


    @Test
    void getProfile_Success() {
        // Given
        String username = USERNAME;

        // When
        ProfileResponseDTO profileResponseDTO = trainerService.getProfile(username);

        // Then
        verify(trainerRepo).findByUsername(username);
        verify(validator).validateTrainerExists(username);
        assertEquals(FIRST_NAME, profileResponseDTO.getFirstName());
        assertEquals(LAST_NAME, profileResponseDTO.getLastName());
        assertEquals(ACTIVE_STATUS, profileResponseDTO.getIsActive());
        assertEquals(1, profileResponseDTO.getTrainees().size());
        assertEquals(TRAINING_TYPE_NAME, profileResponseDTO.getSpecialization().getTrainingTypeName());
    }

    @Test
    void updateProfile_Success() {
        // Given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO(USERNAME, NEW_FIRST_NAME, LAST_NAME, trainingTypeDTO, ACTIVE_STATUS);
        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        when(trainerRepo.findByUsername(USERNAME)).thenReturn(trainer);

        // When
        UpdateResponseDTO result = trainerService.updateProfile(updateRequestDTO);

        // Then
        assertEquals(NEW_FIRST_NAME, result.getFirstName());
        assertEquals(LAST_NAME, result.getLastName());
        Assertions.assertTrue(result.getIsActive());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void updateProfile_UserDoesNotExist() {
        // Given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO(UNKNOWN_USERNAME, FIRST_NAME, LAST_NAME, trainingTypeDTO, ACTIVE_STATUS);
        doThrow(new UserNotFoundException(USER_NOT_FOUND + UNKNOWN_USERNAME)).when(validator).validateUserExists(UNKNOWN_USERNAME);

        // When & Then
        Exception exception = assertThrows(UserNotFoundException.class, () -> trainerService.updateProfile(updateRequestDTO));
        assertEquals(USER_NOT_FOUND + UNKNOWN_USERNAME, exception.getMessage());
    }

    @Test
    void updateProfile_TrainerDoesNotExist() {
        // Given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO(USERNAME, FIRST_NAME, LAST_NAME, trainingTypeDTO, ACTIVE_STATUS);
        doThrow(new TrainerNotFoundException(TRAINER_NOT_FOUND + USERNAME)).when(validator).validateTrainerExists(USERNAME);

        // When & Then
        Exception exception = assertThrows(TrainerNotFoundException.class, () -> trainerService.updateProfile(updateRequestDTO));
        assertEquals(TRAINER_NOT_FOUND + USERNAME, exception.getMessage());
    }

    @Test
    void toggleActivation_Success() {
        // Given
        User existingUser = new User();
        existingUser.setUsername(USERNAME);
        existingUser.setIsActive(!NEW_ACTIVE_STATUS);

        when(userRepo.findByUsername(USERNAME)).thenReturn(existingUser);

        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO(USERNAME, NEW_ACTIVE_STATUS);

        // When
        trainerService.toggleActivation(toggleActivationDTO);

        // Then
        verify(validator).validateUserExists(USERNAME);
        verify(userRepo).findByUsername(USERNAME);
        verify(userRepo).save(existingUser);
        assert existingUser.getIsActive() == NEW_ACTIVE_STATUS : "The active status should be updated.";
    }

}