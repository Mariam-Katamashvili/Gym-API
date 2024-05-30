package com.mariamkatamashvili.gym.repository;

import com.mariamkatamashvili.gym.entity.User;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Generated
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}