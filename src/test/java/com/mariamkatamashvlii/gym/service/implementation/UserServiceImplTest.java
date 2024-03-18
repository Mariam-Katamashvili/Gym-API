package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordDTO;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUserDoesNotExist() {
        String username = "nonExistingUser";
        LoginDTO loginDTO = new LoginDTO(username, "password");
        when(userRepo.findUserByUsername(username)).thenReturn(null);

        boolean result = userServiceImpl.login(loginDTO);

        assertFalse(result);
    }

    @Test
    void testLoginIncorrectPassword() {
        String username = "existingUser";
        String password = "wrongPassword";
        LoginDTO loginDTO = new LoginDTO(username, password);
        User user = User.builder()
                .username("existingUser")
                .password("correctPassword")
                .build();
        when(userRepo.findUserByUsername(username)).thenReturn(user);

        boolean result = userServiceImpl.login(loginDTO);

        assertFalse(result);
    }

    @Test
    void testLoginSuccess() {
        String username = "existingUser";
        String password = "correctPassword";
        LoginDTO loginDTO = new LoginDTO(username, password);
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        when(userRepo.findUserByUsername(username)).thenReturn(user);

        boolean result = userServiceImpl.login(loginDTO);

        assertTrue(result);
    }

    @Test
    void changePassword_UserNotFound() {
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO("nonExistentUser", "currPassword", "newPassword");
        when(userRepo.findUserByUsername("nonExistentUser")).thenReturn(null);

        userServiceImpl.changePassword(newPasswordDTO);

        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void changePassword_Successful() {
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO("existentUser", "currPassword", "newPassword");
        User mockUser = new User();
        mockUser.setUsername("existentUser");
        mockUser.setPassword("currPassword");

        when(userRepo.findUserByUsername("existentUser")).thenReturn(mockUser);

        userServiceImpl.changePassword(newPasswordDTO);

        assertEquals("newPassword", mockUser.getPassword());
        verify(userRepo).save(mockUser);
    }

    @Test
    void changePassword_ThrowsException() {
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO("nonExistentUser", "currPassword", "newPassword");
        when(userRepo.findUserByUsername(anyString())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> userServiceImpl.changePassword(newPasswordDTO));

        verify(userRepo, never()).save(any(User.class));
    }
}
