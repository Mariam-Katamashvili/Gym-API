package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private UsernameGenerator usernameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testDelete() {
        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");

        doNothing().when(userRepo).delete(any(User.class));

        userService.delete(user);

        verify(userRepo).delete(user);
    }

    private final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

}
