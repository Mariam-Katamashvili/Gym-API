package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService implements com.mariamkatamashvlii.gym.service.UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        logger.debug("UserServiceImpl initialized with UserRepo");
    }

    @Override
    @Transactional
    public User create(User user) {
        user.setUsername(generateUserName(user.getFirstName(), user.getLastName()));
        user.setPassword(generatePassword());
        logger.info("Created user with id {}", user.getUserId());
        return userRepo.create(user);
    }

    @Override
    public void update(User user) {
        userRepo.update(user);
        logger.info("Updated user with id {}", user.getUserId());
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepo.delete(id);
        logger.info("Deleted user with id {}", id);
    }

    @Override
    public void delete(String username) {
        userRepo.delete(username);
        logger.info("Deleted user {}", username);
    }


    @Override
    public User select(long id) {
        logger.info("Selecting user with id {}", id);
        return userRepo.select(id);
    }

    @Override
    public User select(String username) {
        logger.info("Selecting user {}", username);
        return userRepo.select(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        User user = select(username);
        logger.info("Checking credentials for user with id {}", user.getUserId());
        return user.getPassword().equals(password);
    }

    @Override
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = select(username);
        if (checkCredentials(username, currentPassword)) {
            user.setPassword(newPassword);
            update(user);
            logger.info("Changed password for user {}", user.getUsername());
            return true;
        }
        logger.info("Could not change password for user {}", user.getUsername());
        return false;
    }

    @Override
    public void toggleActivation(String username, boolean isActive) {
        User user = select(username);
        user.setActive(isActive);
        update(user);
        logger.info("Changed activation status for user {}", user);
    }

    @Override
    public List<User> findAll() {
        logger.info("Returning all users");
        return userRepo.findAll();
    }

    private String generateUserName(String first, String last) {
        List<User> users = findAll();
        List<String> userNames = new ArrayList<>();
        users.forEach(user -> userNames.add(user.getUsername()));
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(first).append(".").append(last);
        while (true) {
            int counterBefore = counter;
            for (String username : userNames) {
                if (username.contentEquals(builder)) {
                    counter++;
                }
            }
            if (counter != 0) {
                builder.setLength(0);
                builder.append(first);
                builder.append(".");
                builder.append(last);
                builder.append(counter);
            }
            if (counterBefore == counter) {
                break;
            }
        }
        logger.info("Generating username");
        return builder.toString();
    }

    private String generatePassword() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            char randomChar = (char) random.nextInt(33, 127);
            builder.append(randomChar);
        }
        logger.info("Generating password");
        return builder.toString();
    }
}
