//package com.mariamkatamashvlii.gym.service.implementation;
//
//import com.mariamkatamashvlii.gym.dto.securityDto.TokenDTO;
//import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
//import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
//import com.mariamkatamashvlii.gym.entity.User;
//import com.mariamkatamashvlii.gym.exception.GymException;
//import com.mariamkatamashvlii.gym.repository.UserRepository;
//import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
//import com.mariamkatamashvlii.gym.validator.Validator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//    private static final String USERNAME = "user";
//    private static final String VALID_PASSWORD = "pass";
//    private static final String NEW_PASSWORD = "newpass";
//    private static final String INVALID_PASSWORD = "wrongpass";
//    private static final String TEST_TOKEN = "token";
//    private static final String AUTHENTICATION_FAILED = "Authentication failed";
//    private static final String PASSWORD_INCORRECT = "Current password is incorrect!";
//    @Mock
//    private UserRepository userRepo;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @Mock
//    private JwtTokenGenerator jwtTokenGenerator;
//    @Mock
//    private Authentication authentication;
//    @Mock
//    private Validator validator;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private LoginRequestDTO loginRequest;
//    private NewPasswordRequestDTO newPasswordRequest;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        loginRequest = new LoginRequestDTO();
//        loginRequest.setUsername(USERNAME);
//        newPasswordRequest = new NewPasswordRequestDTO();
//        newPasswordRequest.setUsername(USERNAME);
//        newPasswordRequest.setNewPass(NEW_PASSWORD);
//        user = new User();
//        user.setPassword(passwordEncoder.encode(VALID_PASSWORD));
//    }
////    @Test
////    void login_success() {
////        // Given
////        loginRequest.setPassword(VALID_PASSWORD);
////
////        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, VALID_PASSWORD)))
////                .thenReturn(authentication);
////        when(jwtTokenGenerator.generateToken(authentication)).thenReturn(TEST_TOKEN);
////
////        // When
////        TokenDTO result = userService.login(loginRequest);
////
////        // Then
////        assertEquals(TEST_TOKEN, result);
////    }
//
//    @Test
//    void login_failure() {
//        // Given
//        loginRequest.setPassword(INVALID_PASSWORD);
//
//        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, INVALID_PASSWORD)))
//                .thenThrow(new GymException(AUTHENTICATION_FAILED));
//
//        // When
//        Exception actualException = assertThrows(GymException.class, () -> userService.login(loginRequest));
//
//        assertEquals(AUTHENTICATION_FAILED, actualException.getMessage());
//    }
//
//    @Test
//    void changePassword_success() {
//        // Given
//        newPasswordRequest.setCurrentPass(VALID_PASSWORD);
//
//        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
//        when(passwordEncoder.matches(VALID_PASSWORD, user.getPassword())).thenReturn(true);
//
//        // When
//        assertDoesNotThrow(() -> userService.changePassword(newPasswordRequest));
//    }
//
//    @Test
//    void changePassword_failure() {
//        // Given
//        newPasswordRequest.setCurrentPass(INVALID_PASSWORD);
//
//        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
//        when(passwordEncoder.matches(INVALID_PASSWORD, user.getPassword())).thenReturn(false);
//
//        // When
//        Exception actualException = assertThrows(GymException.class, () -> userService.changePassword(newPasswordRequest));
//
//        // Then
//        assertEquals(PASSWORD_INCORRECT, actualException.getMessage());
//    }
//}