package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;

    @Override
    @Transactional
    public void delete(User user) {
        userRepo.delete(user);
        log.info("Deleted user - {}", user.getUsername());
    }

    @Override
    public boolean login(String username, String password) {
        if (userRepo.select(username) == null) {
            log.info("Username does not exist");
            return false;
        }
        User user = userRepo.select(username);
        if (user.getPassword().equals(password)) {
            log.info("User {} logged in successfully", username);
            return true;
        } else {
            log.info("Password is incorrect");
            return false;
        }
    }

    @Override
    public void passChange(String username, String currPassword, String newPassword) {
        User user = userRepo.select(username);
        user.setPassword(newPassword);
        userRepo.update(user);
        log.info("Password changed successfully");
    }

    @Override
    public List<User> findAll() {
        log.info("Returning all users");
        return userRepo.findAll();
    }

}