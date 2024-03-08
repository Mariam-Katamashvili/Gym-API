//package com.mariamkatamashvlii.gym.controller;
//
//import com.mariamkatamashvlii.gym.entity.Trainee;
//import com.mariamkatamashvlii.gym.entity.User;
//import com.mariamkatamashvlii.gym.service.TraineeService;
//import com.mariamkatamashvlii.gym.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Date;
//
//@RestController
//@RequestMapping("/trainees")
//public class TraineeController {
//
//    private final TraineeService traineeService;
//    private final UserService userService;
//
//    @Autowired
//    public TraineeController(TraineeService traineeService, UserService userService) {
//        this.traineeService = traineeService;
//        this.userService = userService;
//    }
//
//    @PostMapping("/registration")
//    public Trainee traineeRegistration(@RequestParam(value = "dob", required = false) Date dob, @RequestParam(value = "address", required = false) String address) {
//
//        return null;
//    }
//
//    @GetMapping("/trainees/{username:.+}")
//    public Trainee getTraineeProfile(@RequestParam String username) {
//        User user = userService.select(username);
//        String password = user.getPassword();
//        return traineeService.select(username, password);
//    }
//}
