//package com.mariamkatamashvlii.gym.service.implementation;
//
//import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
//import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
//import com.mariamkatamashvlii.gym.entity.User;
//import com.mariamkatamashvlii.gym.exception.AuthenticationException;
//import com.mariamkatamashvlii.gym.repository.UserRepository;
//import com.mariamkatamashvlii.gym.security.JwtTokenUtil;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private JwtTokenUtil jwtTokenUtil;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private static final String USERNAME = "existingUser";
//    private static final String PASSWORD = "correctPassword";
//    private static final String ENCODED_PASSWORD = "encodedPassword";
//    private static final String NEW_PASSWORD = "newPassword";
//    private static final String WRONG_PASSWORD = "wrongPassword";
//    private static final String TOKEN = "token";
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        user = new User();
//        user.setUsername(USERNAME);
//        user.setPassword(passwordEncoder.encode(PASSWORD));
//    }
//
//    @Test
//    void loginSuccess() {
//        // Given
//        Authentication authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(jwtTokenUtil.generateJwtToken(authentication)).thenReturn(TOKEN); // More explicit setup
//        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(USERNAME, PASSWORD);
//
//        // When
//        String resultToken = userService.login(loginRequestDTO);
//
//        // Then
//        assertNotNull(resultToken, "Token should not be null");
//        assertFalse(resultToken.isEmpty(), "Token should not be empty");
//        assertEquals(TOKEN, resultToken, "The returned token does not match the expected value");
//    }
//
//
//
//    @Test
//    void loginFailurePasswordMismatch() {
//        // Given
//        User user = User.builder().username(USERNAME).password(PASSWORD).build();
//        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
//
//        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(USERNAME, WRONG_PASSWORD);
//
//        // When & Then
//        assertThrows(BadCredentialsException.class, () -> userService.login(loginRequestDTO));
//    }
//
//
//    @Test
//    void changePasswordSuccess() {
//        // Given
//        NewPasswordRequestDTO newPasswordRequestDTO = new NewPasswordRequestDTO(USERNAME, PASSWORD, NEW_PASSWORD);
//        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
//
//        // When
//        userService.changePassword(newPasswordRequestDTO);
//
//        // Then
//        verify(userRepository).save(user);
//        verify(passwordEncoder).encode(NEW_PASSWORD);
//    }
//
//    @Test
//    void changePasswordFailureIncorrectCurrentPassword() {
//        // Given
//        NewPasswordRequestDTO newPasswordRequestDTO = new NewPasswordRequestDTO(USERNAME, WRONG_PASSWORD, NEW_PASSWORD);
//
//        // When & Then
//        assertThrows(AuthenticationException.class, () -> userService.changePassword(newPasswordRequestDTO));
//    }
//}