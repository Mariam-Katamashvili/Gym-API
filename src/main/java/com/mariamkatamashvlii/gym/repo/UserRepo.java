package com.mariamkatamashvlii.gym.repo;

import com.mariamkatamashvlii.gym.model.User;

import java.util.List;

public interface UserRepo {
    void create(User user);

    void update(User user);

    void delete(long id);

    void delete(String username);

    User select(long id);

    User select(String username);

    List<User> findAll();
}
