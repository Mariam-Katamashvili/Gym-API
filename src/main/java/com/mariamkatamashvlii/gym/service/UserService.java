package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User update(User user);

    void delete(User user);

    User select(String username);

    boolean login(String username, String password);

    void passChange(String username, String currPassword, String newPassword);

    List<User> findAll();
}
