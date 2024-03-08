package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    void update(User user);

    void delete(long id);

    void delete(String username);

    User select(long id);

    User select(String username);

    List<User> findAll();
}
