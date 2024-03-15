package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.User;

import java.util.List;

public interface UserService {

    void delete(User user);

    boolean login(String username, String password);

    void passChange(String username, String currPassword, String newPassword);

    List<User> findAll();
}
