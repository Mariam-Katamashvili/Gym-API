package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/user")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/user")
    public User save(@RequestBody User user) {
        userService.create(user);
        return user;
    }

    @GetMapping("/user/{id}")
    public User select(@PathVariable long id) {
        return userService.select(id);
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable long id) {
        userService.delete(id);
        return "user " + id + " removed";
    }
}
