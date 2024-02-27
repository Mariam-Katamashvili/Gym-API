package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void create(User user) {
        user.setUsername(generateUserName(user.getFirstName(), user.getLastName()));
        user.setPassword(generatePassword());
        userRepo.create(user);
    }

    @Override
    public void update(User user) {
        userRepo.update(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepo.delete(id);
    }

    @Override
    public void delete(String username) {
        userRepo.delete(username);
    }

    @Override
    public User select(long id) {
        return userRepo.select(id);
    }

    @Override
    public User select(String username) {
        return userRepo.select(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        User user = select(username);
        if (user.getPassword().equals(password)) return true;
        return false;
    }

    @Override
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = select(username);
        if (checkCredentials(username, currentPassword)) {
            user.setPassword(newPassword);
            update(user);
            return true;
        }
        return false;
    }

    @Override
    public void toggleActivation(String username, boolean isActive) {
        User user = select(username);
        user.setActive(isActive);
        update(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    private String generateUserName(String first, String last) {
        List<User> users = findAll();
        List<String> userNames = new ArrayList<>();
        users.forEach(user -> userNames.add(user.getUsername()));

        //username generating stage
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
        return builder.toString();
    }

    private String generatePassword() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            char randomChar = (char) random.nextInt(33, 127);
            builder.append(randomChar);
        }
        return builder.toString();
    }


}
