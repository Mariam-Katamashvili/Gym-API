//package com.mariamkatamashvlii.gym.controller;
//
//import com.mariamkatamashvlii.gym.model.User;
//import com.mariamkatamashvlii.gym.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/user")
//    public List<User> findAll() {
//        return userService.findAll();
//    }
//
//    @DeleteMapping("/user/id/{id}")
//    public String delete(@PathVariable long id) {
//        userService.delete(id);
//        return "user " + id + " removed";
//    }
//    @GetMapping("/user/{username:.+}")
//    public User getUserByUsername(@PathVariable String username) {
//        return userService.select(username);
//    }
//    @GetMapping("user/check")
//    public boolean checkCredentials(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password
//    ) {
//        return userService.checkCredentials(username, password);
//    }
//    @PutMapping("user/changePassword")
//    public boolean changePassword(
//            @RequestParam("username") String username,
//            @RequestParam("currPassword") String currPassword,
//            @RequestParam("newPassword") String newPassword
//    ) {
//        return userService.changePassword(username, currPassword, newPassword);
//    }
//}