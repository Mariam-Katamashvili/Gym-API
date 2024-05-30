package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.TrainerUsernameDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvili.gym.entity.Trainee;
import com.mariamkatamashvili.gym.entity.Trainer;
import com.mariamkatamashvili.gym.entity.Training;
import com.mariamkatamashvili.gym.entity.TrainingType;
import com.mariamkatamashvili.gym.entity.User;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.generator.PasswordGenerator;
import com.mariamkatamashvili.gym.generator.UsernameGenerator;
import com.mariamkatamashvili.gym.repository.TraineeRepository;
import com.mariamkatamashvili.gym.repository.TrainerRepository;
import com.mariamkatamashvili.gym.repository.TrainingRepository;
import com.mariamkatamashvili.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvili.gym.repository.UserRepository;
import com.mariamkatamashvili.gym.security.GymUserDetails;
import com.mariamkatamashvili.gym.security.JwtTokenGenerator;
import com.mariamkatamashvili.gym.service.TokenService;
import com.mariamkatamashvili.gym.service.implementation.TraineeServiceImpl;
import com.mariamkatamashvili.gym.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    private static final String USERNAME_JOHN_DOE = "johndoe";
    private static final String USERNAME_TRAINER1 = "trainer1";
    private static final String FIRST_NAME_JOHN = "John";
    private static final String LAST_NAME_DOE = "Doe";
    private static final String PASSWORD = "securepassword";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String SPECIALIZATION_NAME = "Yoga";
    private static final Long SPECIALIZATION_ID = 1L;
    private static final boolean IS_ACTIVE = true;
    private static final String USER_NOT_FOUND = "User not found";
    private static final String TRAINEE_NOT_FOUND = "Trainee not found";
    private static final String TRAINING = "Morning Yoga";

    @Mock
    private TraineeRepository traineeRepo;
    @Mock
    private UserRepository userRepo;
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
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenGenerator jwtTokenGenerator;
    @Mock
    private TokenService tokenService;
    @Mock
    private Validator validator;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    @Transactional
    void testRegister() {
        // given
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setFirstName(FIRST_NAME_JOHN);
        registrationRequestDTO.setLastName(LAST_NAME_DOE);

        User user = User.builder().firstName(FIRST_NAME_JOHN).lastName(LAST_NAME_DOE).username(USERNAME_JOHN_DOE).password(ENCODED_PASSWORD).isActive(IS_ACTIVE).build();
        Trainee trainee = Trainee.builder().user(user).build();
        GymUserDetails userDetails = new GymUserDetails(user);
        RegistrationResponseDTO expectedResponse = new RegistrationResponseDTO();

        when(usernameGenerator.generateUsername(FIRST_NAME_JOHN, LAST_NAME_DOE)).thenReturn(USERNAME_JOHN_DOE);
        when(passwordGenerator.generatePassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(tokenService.register(any(GymUserDetails.class), eq(USERNAME_JOHN_DOE), eq(PASSWORD))).thenReturn(expectedResponse);

        // when
        RegistrationResponseDTO actualResponse = traineeService.register(registrationRequestDTO);

        // then
        assertNotNull(actualResponse);
        verify(traineeRepo, times(1)).save(any(Trainee.class));
    }

    @Test
    void testGetProfile() {
        // given
        User user = User.builder().username(USERNAME_JOHN_DOE).firstName(FIRST_NAME_JOHN).lastName(LAST_NAME_DOE).build();
        Trainee trainee = Trainee.builder().user(user).build();

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.of(user));
        when(traineeRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(trainee);

        // when
        ProfileResponseDTO profileResponse = traineeService.getProfile(USERNAME_JOHN_DOE);

        // then
        assertNotNull(profileResponse);
        assertEquals(FIRST_NAME_JOHN, profileResponse.getFirstName());
        assertEquals(LAST_NAME_DOE, profileResponse.getLastName());
    }

    @Test
    @Transactional
    void testUpdateProfile() {
        // given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setUsername(USERNAME_JOHN_DOE);
        updateRequestDTO.setFirstName(FIRST_NAME_JOHN);
        updateRequestDTO.setLastName(LAST_NAME_DOE);

        User user = User.builder().username(USERNAME_JOHN_DOE).build();
        Trainee trainee = Trainee.builder().user(user).build();

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.of(user));
        when(traineeRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(trainee);

        // when
        UpdateResponseDTO updateResponse = traineeService.updateProfile(updateRequestDTO);

        // then
        assertNotNull(updateResponse);
        assertEquals(FIRST_NAME_JOHN, updateResponse.getFirstName());
        assertEquals(LAST_NAME_DOE, updateResponse.getLastName());
    }

    @Test
    void testGetUnassignedTrainers() {
        // given
        TrainingType specialization = TrainingType.builder().id(SPECIALIZATION_ID).trainingTypeName(SPECIALIZATION_NAME).build();
        Trainer trainer = Trainer.builder().user(User.builder().username(USERNAME_TRAINER1).build()).specialization(specialization).build();
        List<Trainer> allTrainers = List.of(trainer);
        Trainee trainee = Trainee.builder().user(User.builder().username(USERNAME_JOHN_DOE).build()).build();

        when(traineeRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(trainee);
        when(trainerRepo.findAll()).thenReturn(allTrainers);

        // when
        List<TrainerDTO> unassignedTrainers = traineeService.getUnassignedTrainers(USERNAME_JOHN_DOE);

        // then
        assertNotNull(unassignedTrainers);
        assertFalse(unassignedTrainers.isEmpty());
    }

    @Test
    void testGetTrainings() {
        // given
        TrainingsRequestDTO trainingsRequestDTO = new TrainingsRequestDTO();
        trainingsRequestDTO.setUsername(USERNAME_JOHN_DOE);
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO(SPECIALIZATION_ID, SPECIALIZATION_NAME);
        trainingsRequestDTO.setTrainingType(trainingTypeDTO);
        trainingsRequestDTO.setStartDate(LocalDate.now().minusDays(1));
        trainingsRequestDTO.setEndDate(LocalDate.now().plusDays(1));

        TrainingType trainingType = TrainingType.builder().id(SPECIALIZATION_ID).trainingTypeName(SPECIALIZATION_NAME).build();
        User trainerUser = User.builder().username(USERNAME_TRAINER1).build();
        Trainer trainer = Trainer.builder().user(trainerUser).specialization(trainingType).build();
        Training training = Training.builder().trainingName(TRAINING).trainingDate(LocalDate.now()).duration(60).trainer(trainer).trainingType(trainingType).build();
        List<Training> trainings = List.of(training);

        when(trainingRepo.findTraineeTrainingsByCriteria(
                eq(USERNAME_JOHN_DOE),
                eq(LocalDate.now().minusDays(1)),
                eq(LocalDate.now().plusDays(1)),
                isNull(),
                eq(SPECIALIZATION_NAME)
        )).thenReturn(trainings);

        when(trainingTypeRepo.findByTrainingTypeName(SPECIALIZATION_NAME)).thenReturn(Optional.of(trainingType));

        // when
        List<TrainingResponseDTO> trainingResponses = traineeService.getTrainings(trainingsRequestDTO);

        // then
        assertNotNull(trainingResponses);
        assertFalse(trainingResponses.isEmpty());
        assertEquals(TRAINING, trainingResponses.get(0).getTrainingName());
    }

    @Test
    @Transactional
    void testUpdateTrainers() {
        // given
        UpdateTrainersRequestDTO updateTrainersRequestDTO = new UpdateTrainersRequestDTO();
        updateTrainersRequestDTO.setUsername(USERNAME_JOHN_DOE);
        TrainerUsernameDTO trainerUsernameDTO = new TrainerUsernameDTO(USERNAME_TRAINER1);
        updateTrainersRequestDTO.setTrainers(List.of(trainerUsernameDTO));

        User user = User.builder().username(USERNAME_JOHN_DOE).build();
        Trainee trainee = Trainee.builder().user(user).build();
        TrainingType trainingType = TrainingType.builder().id(SPECIALIZATION_ID).trainingTypeName(SPECIALIZATION_NAME).build();
        User trainerUser = User.builder().username(USERNAME_TRAINER1).build();
        Trainer trainer = Trainer.builder().user(trainerUser).specialization(trainingType).build();

        when(traineeRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(trainee);
        when(trainerRepo.findByUsername(USERNAME_TRAINER1)).thenReturn(trainer);

        // when
        List<TrainerDTO> updatedTrainers = traineeService.updateTrainers(updateTrainersRequestDTO);

        // then
        assertNotNull(updatedTrainers);
        assertFalse(updatedTrainers.isEmpty());
        assertEquals(USERNAME_TRAINER1, updatedTrainers.get(0).getUsername());
        assertEquals(SPECIALIZATION_NAME, updatedTrainers.get(0).getSpecialization().getTrainingTypeName());
    }

    @Test
    @Transactional
    void testToggleActivation() {
        // given
        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO();
        toggleActivationDTO.setUsername(USERNAME_JOHN_DOE);
        toggleActivationDTO.setIsActive(IS_ACTIVE);

        User user = User.builder().username(USERNAME_JOHN_DOE).build();

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.of(user));

        // when
        traineeService.toggleActivation(toggleActivationDTO);

        // then
        assertTrue(user.getIsActive());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testRegister_WithException() {
        // given
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setFirstName(FIRST_NAME_JOHN);
        registrationRequestDTO.setLastName(LAST_NAME_DOE);

        when(usernameGenerator.generateUsername(FIRST_NAME_JOHN, LAST_NAME_DOE)).thenThrow(new RuntimeException("Error"));

        // when
        GymException exception = assertThrows(GymException.class, () -> traineeService.register(registrationRequestDTO));

        // then
        assertTrue(exception.getMessage().contains("Could not create trainee due to an error: Error"));
    }

    @Test
    void testGetProfile_UserNotFound() {
        // given
        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.empty());

        // when
        GymException exception = assertThrows(GymException.class, () -> traineeService.getProfile(USERNAME_JOHN_DOE));

        // then
        assertTrue(exception.getMessage().contains(USER_NOT_FOUND));
    }

    @Test
    void testGetProfile_TraineeNotFound() {
        // given
        User user = User.builder().username(USERNAME_JOHN_DOE).build();

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.of(user));
        when(traineeRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(null);

        // when
        GymException exception = assertThrows(GymException.class, () -> traineeService.getProfile(USERNAME_JOHN_DOE));

        // then
        assertTrue(exception.getMessage().contains(TRAINEE_NOT_FOUND));
    }

    @Test
    void testUpdateProfile_UserNotFound() {
        // given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setUsername(USERNAME_JOHN_DOE);

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.empty());

        // when
        GymException exception = assertThrows(GymException.class, () -> traineeService.updateProfile(updateRequestDTO));

        // then
        assertTrue(exception.getMessage().contains(USER_NOT_FOUND));
    }

    @Test
    void testUpdateProfile_TraineeNotFound() {
        // given
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setUsername(USERNAME_JOHN_DOE);
        User user = User.builder().username(USERNAME_JOHN_DOE).build();

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.of(user));
        when(traineeRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(null);

        // when
        GymException exception = assertThrows(GymException.class, () -> traineeService.updateProfile(updateRequestDTO));

        // then
        assertTrue(exception.getMessage().contains(TRAINEE_NOT_FOUND));
    }

    @Test
    void testToggleActivation_UserNotFound() {
        // given
        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO();
        toggleActivationDTO.setUsername(USERNAME_JOHN_DOE);

        when(userRepo.findByUsername(USERNAME_JOHN_DOE)).thenReturn(Optional.empty());

        // when
        GymException exception = assertThrows(GymException.class, () -> traineeService.toggleActivation(toggleActivationDTO));

        // then
        assertTrue(exception.getMessage().contains(USER_NOT_FOUND));
    }
}