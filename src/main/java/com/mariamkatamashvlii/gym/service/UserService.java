package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.User;

import java.util.List;

public interface UserService {
    void create(User user);

    void update(User user);

    void delete(long id);

    void delete(String username);

    User select(long id);

    User select(String username);

    boolean checkCredentials(String username, String password);

    boolean changePassword(String username, String currentPassword, String newPassword);

    void toggleActivation(String username, boolean isActive);

    List<User> findAll();
}
