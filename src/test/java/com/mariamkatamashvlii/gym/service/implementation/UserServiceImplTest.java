package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.AuthenticationException;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.JwtTokenUtil;
import com.mariamkatamashvlii.gym.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String USERNAME = "user";
    private static final String VALID_PASSWORD = "pass";
    private static final String NEW_PASSWORD = "newpass";
    private static final String INVALID_PASSWORD = "wrongpass";
    private static final String TOKEN = "token";
    private static final String AUTHENTICATION_FAILED = "Authentication failed";

    @Mock
    private UserRepository userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private Authentication authentication;
    @Mock
    private Validator validator;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginSuccessTest() {
        // Given
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(USERNAME);
        loginRequest.setPassword(VALID_PASSWORD);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, VALID_PASSWORD)))
                .thenReturn(authentication);
        when(jwtTokenUtil.generateJwtToken(authentication)).thenReturn(TOKEN);

        // When
        String result = userService.login(loginRequest);

        // Then
        assertEquals(TOKEN, result);
    }

    @Test
    void loginFailureTest() {
        // Given
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(USERNAME);
        loginRequest.setPassword(INVALID_PASSWORD);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, INVALID_PASSWORD)))
                .thenThrow(new AuthenticationException(AUTHENTICATION_FAILED));

        // When
        Exception exception = assertThrows(AuthenticationException.class, () -> userService.login(loginRequest));

        assertEquals(AUTHENTICATION_FAILED, exception.getMessage());
    }

    @Test
    void changePasswordSuccessTest() {
        // Given
        NewPasswordRequestDTO newPasswordRequest = new NewPasswordRequestDTO();
        newPasswordRequest.setUsername(USERNAME);
        newPasswordRequest.setCurrentPass(VALID_PASSWORD);
        newPasswordRequest.setNewPass(NEW_PASSWORD);

        User user = new User();
        user.setPassword(passwordEncoder.encode(VALID_PASSWORD));

        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        when(passwordEncoder.matches(VALID_PASSWORD, user.getPassword())).thenReturn(true);

        // When
        assertDoesNotThrow(() -> userService.changePassword(newPasswordRequest));
    }

    @Test
    void changePasswordFailureTest() {
        // Given
        NewPasswordRequestDTO newPasswordRequest = new NewPasswordRequestDTO();
        newPasswordRequest.setUsername(USERNAME);
        newPasswordRequest.setCurrentPass(INVALID_PASSWORD);
        newPasswordRequest.setNewPass(NEW_PASSWORD);

        User user = new User();
        user.setPassword(passwordEncoder.encode(VALID_PASSWORD));

        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        when(passwordEncoder.matches(INVALID_PASSWORD, user.getPassword())).thenReturn(false);

        // When
        Exception exception = assertThrows(AuthenticationException.class, () -> userService.changePassword(newPasswordRequest));

        // Then
        assertEquals("Current password is incorrect!", exception.getMessage());
    }
}