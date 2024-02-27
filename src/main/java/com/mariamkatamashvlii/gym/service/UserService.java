package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.User;

import java.util.List;

public interface UserService {
    void create(User user);
    void update(User user);
    void delete(long id);
    User select(long id);
    List<User> findAll();
}
