package com.mariamkatamashvlii.gym.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        doNothing().when(userRepo).create(any(User.class));

        userService.create(user);

        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());
        verify(userRepo).create(user);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("johndoe");
        user.setPassword("password");

        doNothing().when(userRepo).update(any(User.class));

        userService.update(user);

        verify(userRepo).update(user);
    }

    @Test
    void testDeleteById() {
        long userId = 1L;

        doNothing().when(userRepo).delete(userId);

        userService.delete(userId);

        verify(userRepo).delete(userId);
    }

    @Test
    void testDeleteByUsername() {
        String username = "johndoe";

        doNothing().when(userRepo).delete(username);

        userService.delete(username);

        verify(userRepo).delete(username);
    }

    @Test
    void testSelectById() {
        long id = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(id);

        when(userRepo.select(id)).thenReturn(expectedUser);

        User actualUser = userService.select(id);

        assertEquals(expectedUser, actualUser);
        verify(userRepo).select(id);
    }

    @Test
    void testSelectByUsername() {
        String username = "johndoe";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        when(userRepo.select(username)).thenReturn(expectedUser);

        User actualUser = userService.select(username);

        assertEquals(expectedUser, actualUser);
        verify(userRepo).select(username);
    }

    @Test
    void testCheckCredentials() {
        String username = "johndoe";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepo.select(username)).thenReturn(user);

        assertTrue(userService.checkCredentials(username, password));
    }

}
