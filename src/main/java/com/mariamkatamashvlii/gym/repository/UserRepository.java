package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.User;
import lombok.Generated;

import java.util.List;

@Generated
public interface UserRepository {
    User create(User user);

    User update(User user);

    void delete(User user);

    User select(long id);

    User select(String username);

    List<User> findAll();
}
