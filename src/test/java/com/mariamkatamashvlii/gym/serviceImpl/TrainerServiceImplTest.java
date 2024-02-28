package com.mariamkatamashvlii.gym.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.repo.TrainerRepo;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class TrainerServiceImplTest {

    @Mock
    private TrainerRepo trainerRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserService userService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUserId(1L);
        trainer.setUser(user);

        when(userRepo.select(user.getUserId())).thenReturn(user);
        doNothing().when(trainerRepo).create(trainer);

        trainerService.create(trainer);

        verify(trainerRepo).create(trainer);
    }

    @Test
    void testUpdate() {
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUserId(1L);
        user.setUsername("trainerUser");
        user.setPassword("password");
        trainer.setUser(user);

        when(userRepo.select(user.getUserId())).thenReturn(user);
        when(userService.checkCredentials(user.getUsername(), user.getPassword())).thenReturn(true);
        doNothing().when(trainerRepo).update(trainer);

        trainerService.update(trainer);

        verify(trainerRepo).update(trainer);
    }

    @Test
    void testDelete() {
        long id = 1L;

        doNothing().when(trainerRepo).delete(id);

        trainerService.delete(id);

        verify(trainerRepo).delete(id);
    }

    @Test
    void testSelectById() {
        long id = 1L;
        Trainer expectedTrainer = new Trainer();

        when(trainerRepo.select(id)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerService.select(id);

        assertEquals(expectedTrainer, actualTrainer);
        verify(trainerRepo).select(id);
    }

    @Test
    void testSelectByUsernameAndPassword() {
        String username = "trainerUser";
        String password = "password";
        Trainer expectedTrainer = new Trainer();

        when(userService.checkCredentials(username, password)).thenReturn(true);
        when(trainerRepo.select(username)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerService.select(username, password);

        assertEquals(expectedTrainer, actualTrainer);
        verify(trainerRepo).select(username);
    }

    @Test
    void testCheckCredentials() {
        String username = "trainerUser";
        String password = "password";

        when(userService.checkCredentials(username, password)).thenReturn(true);

        boolean result = trainerService.checkCredentials(username, password);

        assertTrue(result);
        verify(userService).checkCredentials(username, password);
    }

    @Test
    void testChangePassword() {
        String username = "trainerUser";
        String currPassword = "password";
        String newPassword = "newPassword";

        when(userService.changePassword(username, currPassword, newPassword)).thenReturn(true);

        boolean result = trainerService.changePassword(username, currPassword, newPassword);

        assertTrue(result);
        verify(userService).changePassword(username, currPassword, newPassword);
    }

    @Test
    void testToggleActivation() {
        String username = "trainerUser";
        String password = "password";
        boolean isActive = true;

        when(userService.checkCredentials(username, password)).thenReturn(true);

        trainerService.toggleActivation(username, password, isActive);

        verify(userService).toggleActivation(username, isActive);
    }

    @Test
    void testFindAll() {
        List<Trainer> expectedTrainers = new ArrayList<>();

        when(trainerRepo.findAll()).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = trainerService.findAll();

        assertEquals(expectedTrainers, actualTrainers);
        verify(trainerRepo).findAll();
    }

}
