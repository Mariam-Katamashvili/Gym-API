package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    void update(User user);
    void delete(long userId);
    User select(long userId);
    List<User> findAll();
}
