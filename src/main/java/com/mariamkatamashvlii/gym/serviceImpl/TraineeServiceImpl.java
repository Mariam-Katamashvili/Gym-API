package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.model.*;
import com.mariamkatamashvlii.gym.repo.TraineeRepo;
import com.mariamkatamashvlii.gym.repo.TrainerRepo;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.service.TraineeService;
import com.mariamkatamashvlii.gym.service.TrainingService;
import com.mariamkatamashvlii.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeRepo traineeRepo;
    private UserRepo userRepo;
    private UserService userService;
    private TrainingService trainingService;
    private TrainerRepo trainerRepo;

    @Autowired
    public TraineeServiceImpl(TraineeRepo traineeRepo, UserRepo userRepo,
                              UserService userService, TrainingService trainingService, TrainerRepo trainerRepo) {
        this.traineeRepo = traineeRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.trainingService = trainingService;
        this.trainerRepo = trainerRepo;
    }

    @Override
    public void create(Trainee trainee) {
        User user = userRepo.select(trainee.getUser().getUserId());
        if (trainee.getDob() != null && trainee.getAddress() != null && user != null) {
            traineeRepo.create(trainee);
        }
    }

    @Override
    public void update(Trainee trainee) {
        User user = userRepo.select(trainee.getUser().getUserId());
        if (trainee.getDob() != null && trainee.getAddress() != null && user != null) {
            if (userService.checkCredentials(trainee.getUser().getUsername(), trainee.getUser().getPassword())) {
                traineeRepo.update(trainee);
            }
        }
    }

    @Override
    public void delete(long id) {
        traineeRepo.delete(id);
    }

    @Override
    public void delete(String username, String password) {
        Trainee trainee = traineeRepo.select(username);
        Set<Training> trainingSet = trainee.getTrainingSet();
        if (userService.checkCredentials(username, password)) {
            for (Training t : trainingSet) {
                trainingService.delete(t.getTrainingId());
            }
            traineeRepo.delete(username);
        }
    }

    @Override
    public Trainee select(long id) {
        return traineeRepo.select(id);
    }

    @Override
    public Trainee select(String username, String password) {
        if (userService.checkCredentials(username, password)) {
            return traineeRepo.select(username);
        }
        return null;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return userService.checkCredentials(username, password);
    }

    @Override
    public boolean changePassword(String username, String currPassword, String newPassword) {
        return userService.changePassword(username, currPassword, newPassword);
    }

    @Override
    public void toggleActivation(String username, String password, boolean isActive) {
        if (userService.checkCredentials(username, password)) {
            userService.toggleActivation(username, isActive);
        }
    }

    @Override
    public void updateTrainerList(String username, String password, List<Trainer> trainerList) {
        if (userService.checkCredentials(username, password)) {
            User user = userRepo.select(username);
            Trainee trainee = user.getTrainee();
            trainee.setTrainerList(trainerList);
            traineeRepo.update(trainee);
            userRepo.update(user);
        }
    }

    @Override
    public List<Trainee> findAll() {
        return traineeRepo.findAll();
    }

    @Override
    public void createTraineeProfile(Date dob, String address, long userId) {
        Trainee trainee = new Trainee(dob, address);
        User user = userRepo.select(userId);
        trainee.setUser(user);
        create(trainee);
    }

    @Override
    public List<Training> geTrainingList(String username, String password, Date fromDate, Date toDate, String trainerName, TrainingType trainingType) {
        List<Training> trainingList = new ArrayList<>();
        if (userService.checkCredentials(username, password)) {
            Trainee trainee = traineeRepo.select(username);
            Set<Training> trainings = trainee.getTrainingSet();
            for (Training t : trainings) {
                if (isBetween(t.getTrainingDate(), fromDate, toDate) &&
                        t.getTrainer().getUser().getFirstName().equals(trainerName) &&
                        t.getTrainingType().getTrainingTypeName().equals(trainingType.getTrainingTypeName())) {
                    trainingList.add(t);
                }
            }
        }
        return trainingList;
    }

    @Override
    public List<Trainer> getNotAssignedTrainerList(String username, String password) {
        List<Trainer> notAssignedTrainerList = new ArrayList<>();
        if (userService.checkCredentials(username, password)) {
            Trainee trainee = traineeRepo.select(username);
            List<Trainer> traineeTrainers = trainee.getTrainerList();
            List<Trainer> allTrainers = trainerRepo.findAll();
            for (Trainer t : allTrainers) {
                if (!traineeTrainers.contains(t)) {
                    notAssignedTrainerList.add(t);
                }
            }
        }
        return notAssignedTrainerList;
    }

    public boolean isBetween(Date trainingdate, Date fromDate, Date toDate) {
        return trainingdate.compareTo(fromDate) >= 0 && trainingdate.compareTo(toDate) <= 0;
    }
}
