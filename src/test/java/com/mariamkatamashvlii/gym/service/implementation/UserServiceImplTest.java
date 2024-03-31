package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.AuthenticationException;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String USERNAME = "existingUser";
    private static final String PASSWORD = "correctPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String WRONG_PASSWORD = "wrongPassword";

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
    }

    @Test
    void loginSuccess() {
        // Given
        User user = User.builder().username(USERNAME).password(PASSWORD).build();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(USERNAME, PASSWORD);

        // When
        boolean result = userService.login(loginRequestDTO);

        // Then
        assertTrue(result);
        verify(validator).validateUserExists(USERNAME);
    }

    @Test
    void loginFailurePasswordMismatch() {
        // Given
        User user = User.builder().username(USERNAME).password(WRONG_PASSWORD).build();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(USERNAME, PASSWORD);

        // When & Then
        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> userService.login(loginRequestDTO));
        assertEquals("Password is incorrect.", thrown.getMessage());
    }

    @Test
    void changePasswordSuccess() {
        // Given
        User user = User.builder().username(USERNAME).password(PASSWORD).build();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        NewPasswordRequestDTO newPasswordRequestDTO = new NewPasswordRequestDTO(USERNAME, PASSWORD, NEW_PASSWORD);

        // When
        userService.changePassword(newPasswordRequestDTO);

        // Then
        assertEquals(NEW_PASSWORD, user.getPassword());
        verify(userRepository).save(user);
        verify(validator).validateUserExists(USERNAME);
    }

    @Test
    void changePasswordFailureIncorrectCurrentPassword() {
        // Given
        User user = User.builder().username(USERNAME).password(WRONG_PASSWORD).build();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        NewPasswordRequestDTO newPasswordRequestDTO = new NewPasswordRequestDTO(USERNAME, PASSWORD, NEW_PASSWORD);

        // When & Then
        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> userService.changePassword(newPasswordRequestDTO));
        assertEquals("Current password is incorrect!", thrown.getMessage());
    }

}
