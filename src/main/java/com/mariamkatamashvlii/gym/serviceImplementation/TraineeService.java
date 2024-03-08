package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.*;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
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
public class TraineeService implements com.mariamkatamashvlii.gym.service.TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);
    private final TraineeRepository traineeRepo;
    private final UserRepository userRepo;
    private final UserService userService;
    private final TrainingService trainingService;
    private final TrainerRepository trainerRepo;

    @Autowired
    public TraineeService(TraineeRepository traineeRepo, UserRepository userRepo,
                          UserService userService, TrainingService trainingService, TrainerRepository trainerRepo) {
        this.traineeRepo = traineeRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.trainingService = trainingService;
        this.trainerRepo = trainerRepo;
        logger.debug("TraineeServiceImpl initialized with TraineeRepo, UserRepo, UserService, TrainingService and TrainerRepo");
    }

    @Override
    public void create(Trainee trainee) {
        User user = userRepo.select(trainee.getUser().getUserId());
        if (trainee.getDob() != null && trainee.getAddress() != null && user != null) {
            traineeRepo.create(trainee);
            logger.info("Created trainee with id {}", trainee.getTraineeId());
        }
    }

    @Override
    public void update(Trainee trainee) {
        User user = userRepo.select(trainee.getUser().getUserId());
        if (trainee.getDob() != null && trainee.getAddress() != null && user != null && (userService.checkCredentials(trainee.getUser().getUsername(), trainee.getUser().getPassword()))) {
            traineeRepo.update(trainee);
            logger.info("Updated trainee with id {}", trainee.getTraineeId());
        }
    }

    @Override
    public void delete(long id) {
        traineeRepo.delete(id);
        logger.info("Deleted trainee with id {}", id);
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
            logger.info("Deleted trainee with username {}", trainee.getUser().getUsername());
        }
    }

    @Override
    public Trainee select(long id) {
        logger.info("Selecting trainee with id {}", id);
        return traineeRepo.select(id);
    }

    @Override
    public Trainee select(String username, String password) {
        if (userService.checkCredentials(username, password)) {
            logger.info("Selecting trainee with username {}", username);
            return traineeRepo.select(username);
        }
        logger.info("Could not select trainee with username {}", username);
        return null;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        logger.info("Checking credentials of trainee with username - {}", username);
        return userService.checkCredentials(username, password);
    }

    @Override
    public boolean changePassword(String username, String currPassword, String newPassword) {
        logger.info("Changing password for username - {}", username);
        return userService.changePassword(username, currPassword, newPassword);
    }

    @Override
    public void toggleActivation(String username, String password, boolean isActive) {
        if (userService.checkCredentials(username, password)) {
            userService.toggleActivation(username, isActive);
            logger.info("Changed Activation status for trainee with username - {}", username);
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
            logger.info("Updated trainer list for trainee with username - {}", trainee.getUser().getUsername());
        }
    }

    @Override
    public List<Trainee> findAll() {
        logger.info("Returning all trainees");
        return traineeRepo.findAll();
    }

    @Override
    public void createTraineeProfile(Date dob, String address, long userId) {
        Trainee trainee = new Trainee(dob, address);
        User user = userRepo.select(userId);
        trainee.setUser(user);
        create(trainee);
        logger.info("Created trainee profile with id {}", trainee.getTraineeId());
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
        logger.info("Returning training list for trainee {}", username);
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
        logger.info("Returning not assigned trainer list for trainee {}", username);
        return notAssignedTrainerList;
    }

    public boolean isBetween(Date trainingdate, Date fromDate, Date toDate) {
        return trainingdate.compareTo(fromDate) >= 0 && trainingdate.compareTo(toDate) <= 0;
    }
}
