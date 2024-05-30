package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvili.gym.entity.Trainee;
import com.mariamkatamashvili.gym.entity.Trainer;
import com.mariamkatamashvili.gym.entity.Training;
import com.mariamkatamashvili.gym.entity.TrainingType;
import com.mariamkatamashvili.gym.entity.User;
import com.mariamkatamashvili.gym.generator.PasswordGenerator;
import com.mariamkatamashvili.gym.generator.UsernameGenerator;
import com.mariamkatamashvili.gym.security.GymUserDetails;
import com.mariamkatamashvili.gym.security.JwtTokenGenerator;
import com.mariamkatamashvili.gym.service.TokenService;
import com.mariamkatamashvili.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvili.gym.repository.TrainerRepository;
import com.mariamkatamashvili.gym.repository.TrainingRepository;
import com.mariamkatamashvili.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvili.gym.repository.UserRepository;
import com.mariamkatamashvili.gym.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {
    private static final String USERNAME = "testUsername";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encodedPassword123";
    private static final Long TRAINING_TYPE_ID = 1L;
    private static final String TRAINING_TYPE_NAME = "Yoga";
    private static final String ERROR_MESSAGE = "Could not create trainer due to an unexpected error";

    @Mock
    private TrainerRepository trainerRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private TrainingTypeRepository trainingTypeRepo;
    @Mock
    private UsernameGenerator usernameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private Validator validator;
    @Mock
    private TrainingRepository trainingRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenGenerator jwtTokenGenerator;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    @Transactional
    void testRegister() {
        // given
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setFirstName(FIRST_NAME);
        registrationRequestDTO.setLastName(LAST_NAME);
        TrainingTypeDTO specialization = new TrainingTypeDTO();
        specialization.setTrainingTypeId(TRAINING_TYPE_ID);
        registrationRequestDTO.setSpecialization(specialization);

        User user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .password(ENCODED_PASSWORD)
                .isActive(true)
                .build();

        TrainingType trainingType = TrainingType.builder()
                .id(TRAINING_TYPE_ID)
                .trainingTypeName(TRAINING_TYPE_NAME)
                .build();

        Trainer trainer = Trainer.builder()
                .specialization(trainingType)
                .user(user)
                .build();

        GymUserDetails userDetails = new GymUserDetails(user);
        RegistrationResponseDTO expectedResponse = new RegistrationResponseDTO();

        when(usernameGenerator.generateUsername(anyString(), anyString())).thenReturn(USERNAME);
        when(passwordGenerator.generatePassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(trainingTypeRepo.findById(TRAINING_TYPE_ID)).thenReturn(Optional.of(trainingType));
        when(tokenService.register(any(GymUserDetails.class), anyString(), anyString())).thenReturn(expectedResponse);

        // when
        RegistrationResponseDTO actualResponse = trainerService.register(registrationRequestDTO);

        // then
        assertNotNull(actualResponse);
        verify(trainerRepo, times(1)).save(any(Trainer.class));
        verify(tokenService, times(1)).register(any(GymUserDetails.class), eq(USERNAME), eq(PASSWORD));
    }

    @Test
    void testGetProfile() {
        // given
        String username = USERNAME;
        TrainingType trainingType = TrainingType.builder().id(TRAINING_TYPE_ID).trainingTypeName(TRAINING_TYPE_NAME).build();
        User user = User.builder().username(username).firstName(FIRST_NAME).lastName(LAST_NAME).isActive(true).build();
        Trainer trainer = Trainer.builder().user(user).specialization(trainingType).trainees(new ArrayList<>()).build();

        when(trainerRepo.findByUsername(anyString())).thenReturn(trainer);
        doNothing().when(validator).validateTrainerExists(anyString());

        // when
        ProfileResponseDTO profile = trainerService.getProfile(username);

        // then
        assertNotNull(profile);
        assertEquals(FIRST_NAME, profile.getFirstName());
        assertEquals(LAST_NAME, profile.getLastName());
        Assertions.assertEquals(TRAINING_TYPE_NAME, profile.getSpecialization().getTrainingTypeName());
        verify(trainerRepo, times(1)).findByUsername(anyString());
    }

    @Test
    @Transactional
    void testUpdateProfile() {
        // given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setUsername(USERNAME);
        updateRequestDTO.setFirstName(FIRST_NAME);
        updateRequestDTO.setLastName(LAST_NAME);
        updateRequestDTO.setIsActive(true);

        TrainingType trainingType = TrainingType.builder().id(TRAINING_TYPE_ID).trainingTypeName(TRAINING_TYPE_NAME).build();
        User user = User.builder().username(USERNAME).firstName(FIRST_NAME).lastName(LAST_NAME).isActive(true).build();
        Trainer trainer = Trainer.builder().user(user).specialization(trainingType).build();

        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(trainerRepo.findByUsername(anyString())).thenReturn(trainer);
        doNothing().when(validator).validateUserExists(anyString());

        // when
        UpdateResponseDTO updateResponse = trainerService.updateProfile(updateRequestDTO);

        // then
        assertNotNull(updateResponse);
        assertEquals(USERNAME, updateResponse.getUsername());
        assertEquals(FIRST_NAME, updateResponse.getFirstName());
        assertEquals(LAST_NAME, updateResponse.getLastName());
        assertEquals(true, updateResponse.getIsActive());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testGetTrainings() {
        // given
        TrainingsRequestDTO trainingsRequestDTO = new TrainingsRequestDTO();
        trainingsRequestDTO.setUsername(USERNAME);
        trainingsRequestDTO.setStartDate(LocalDate.now().minusDays(10));
        trainingsRequestDTO.setEndDate(LocalDate.now().plusDays(10));

        TrainingType trainingType = TrainingType.builder().id(TRAINING_TYPE_ID).trainingTypeName(TRAINING_TYPE_NAME).build();
        User user = User.builder().username(USERNAME).build();
        Trainer trainer = Trainer.builder().user(user).specialization(trainingType).build();
        User traineeUser = User.builder().username("traineeUsername").build();
        Trainee trainee = Trainee.builder().user(traineeUser).build();

        Training training = Training.builder()
                .trainingName("Test Training")
                .trainingDate(LocalDate.now())
                .duration(60)
                .trainer(trainer)
                .trainee(trainee)
                .trainingType(trainingType)
                .build();

        List<Training> trainings = List.of(training);

        when(trainingRepo.findTrainerTrainingsByCriteria(
                eq(USERNAME),
                any(LocalDate.class),
                any(LocalDate.class),
                isNull()
        )).thenReturn(trainings);

        when(trainingTypeRepo.findByTrainingTypeName(anyString()))
                .thenReturn(Optional.of(trainingType));

        doNothing().when(validator).validateTrainerExists(anyString());

        // when
        List<TrainingResponseDTO> trainingResponses = trainerService.getTrainings(trainingsRequestDTO);

        // then
        assertNotNull(trainingResponses);
        assertFalse(trainingResponses.isEmpty());
        verify(trainingRepo, times(1)).findTrainerTrainingsByCriteria(
                eq(USERNAME),
                any(LocalDate.class),
                any(LocalDate.class),
                isNull()
        );
    }

    @Test
    @Transactional
    void testToggleActivation() {
        // given
        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO();
        toggleActivationDTO.setUsername(USERNAME);
        toggleActivationDTO.setIsActive(false);

        User user = User.builder().username(USERNAME).isActive(true).build();

        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(user));
        doNothing().when(validator).validateUserExists(anyString());

        // when
        trainerService.toggleActivation(toggleActivationDTO);

        // then
        assertEquals(false, user.getIsActive());
        verify(userRepo, times(1)).save(user);
    }
}