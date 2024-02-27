package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.dao.UserDao;
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
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void create(User user) {
        user.setUsername(generateUserName(user.getFirstName(), user.getLastName()));
        user.setPassword(generatePassword());
        userDao.create(user);
    }
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public User select(long id) {
        return userDao.select(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
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
