package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvili.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvili.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvili.gym.entity.Token;
import com.mariamkatamashvili.gym.entity.User;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.repository.TokenRepository;
import com.mariamkatamashvili.gym.security.JwtTokenGenerator;
import com.mariamkatamashvili.gym.service.LoginAttemptsService;
import com.mariamkatamashvili.gym.repository.UserRepository;
import com.mariamkatamashvili.gym.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String VALID_USERNAME = "validUser";
    private static final String VALID_PASSWORD = "validPass";
    private static final String INVALID_USERNAME = "invalidUser";
    private static final String OLD_PASSWORD = "oldPass";
    private static final String NEW_PASSWORD = "newPass";
    private static final String ENCODED_OLD_PASSWORD = "encodedOldPass";
    private static final String MOCK_TOKEN = "mockToken";
    private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials.";
    private static final String CURRENT_PASSWORD_INCORRECT_MESSAGE = "Current password is incorrect!";
    private static final Long ID = 1L;

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenGenerator jwtTokenGenerator;
    @Mock
    private LoginAttemptsService loginAttemptsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Validator validator;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testLogin_WhenCredentialsAreValid_ThenReturnTokenDto() {
        // given
        LoginRequestDTO loginRequest = new LoginRequestDTO(VALID_USERNAME, VALID_PASSWORD);
        User user = User.builder().id(ID).username(VALID_USERNAME).build();
        Token token = new Token();

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        when(jwtTokenGenerator.generateToken(any())).thenReturn(MOCK_TOKEN);
        when(jwtTokenGenerator.extractUsername(MOCK_TOKEN)).thenReturn(VALID_USERNAME);
        when(jwtTokenGenerator.getExpiration(MOCK_TOKEN)).thenReturn(new Date(System.currentTimeMillis() + 20000000));
        when(tokenRepository.findByUserId(ID)).thenReturn(List.of(token));
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        // when
        TokenDTO tokenDTO = userService.login(loginRequest);

        // then
        assertNotNull(tokenDTO);
        Assertions.assertEquals(MOCK_TOKEN, tokenDTO.getToken());
        Assertions.assertEquals(VALID_USERNAME, tokenDTO.getUsername());

        verify(tokenRepository, times(1)).findByUserId(1L);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void testLogin_WhenCredentialsAreInvalid_ThenThrowGymException() {
        // given
        LoginRequestDTO loginRequest = new LoginRequestDTO(VALID_USERNAME, VALID_PASSWORD);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);

        // when
        GymException exception = assertThrows(
                GymException.class,
                () -> userService.login(loginRequest)
        );

        // then
        Assertions.assertEquals(WRONG_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(loginAttemptsService).loginFailed(anyString());
    }

    @Test
    @Transactional
    void testChangePassword_WhenCredentialsAreValid_ThenReturnTokenDto() {
        // given
        String username = VALID_USERNAME;
        NewPasswordRequestDTO newPasswordRequest = new NewPasswordRequestDTO(username, OLD_PASSWORD, NEW_PASSWORD);
        User user = User.builder().id(ID).username(username).password(ENCODED_OLD_PASSWORD).build();
        Token token = new Token();
        token.setId(ID);
        token.setJwtToken(MOCK_TOKEN);

        when(loginAttemptsService.isLockedOut(anyString())).thenReturn(false);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtTokenGenerator.generateToken(any())).thenReturn(token.getJwtToken());
        when(jwtTokenGenerator.extractUsername(MOCK_TOKEN)).thenReturn(VALID_USERNAME);
        when(jwtTokenGenerator.getExpiration(MOCK_TOKEN)).thenReturn(new Date(System.currentTimeMillis() + 20000000));
        when(tokenRepository.findByUserId(ID)).thenReturn(List.of(token));
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        // when
        TokenDTO tokenDTO = userService.changePassword(newPasswordRequest);

        // then
        assertNotNull(tokenDTO);
        Assertions.assertEquals(token.getJwtToken(), tokenDTO.getToken());
        Assertions.assertEquals(VALID_USERNAME, tokenDTO.getUsername());

        verify(loginAttemptsService, times(1)).isLockedOut(anyString());
        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(tokenRepository, times(1)).findByUserId(ID);
    }

    @Test
    @Transactional
    void testChangePassword_WhenCredentialsAreInvalid_ThenThrowGymException() {
        // given
        String username = INVALID_USERNAME;
        NewPasswordRequestDTO newPasswordRequest = new NewPasswordRequestDTO(username, OLD_PASSWORD, NEW_PASSWORD);
        User user = User.builder().id(ID).username(username).password(ENCODED_OLD_PASSWORD).build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // when
        GymException exception = assertThrows(
                GymException.class,
                () -> userService.changePassword(newPasswordRequest)
        );

        // then
        Assertions.assertEquals(CURRENT_PASSWORD_INCORRECT_MESSAGE, exception.getMessage());
        verify(loginAttemptsService, times(1)).isLockedOut(anyString());
    }
}