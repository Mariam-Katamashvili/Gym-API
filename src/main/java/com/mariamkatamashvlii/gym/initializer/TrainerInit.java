package com.mariamkatamashvlii.gym.initializer;

import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.TrainingType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class TrainerInit {
//    private static final Logger logger = LoggerFactory.getLogger(TrainerInit.class);
//    private Map<Long, Trainee> traineeMap;
//    private Map<Long, Trainer> trainerMap;
//
//    @Value("${data.file.path.trainer}")
//    private String datapath;
//
//    @Autowired
//    public void setTraineeMap(Map<Long, Trainee> traineeMap) {
//        this.traineeMap = traineeMap;
//    }
//
//    @Autowired
//    public void setTrainerMap(Map<Long, Trainer> trainerMap) {
//        this.trainerMap = trainerMap;
//    }
//
//    @PostConstruct
//    public void initialize() {
//        logger.info("Initializing trainers from data path: {}", datapath);
//        ClassPathResource resource = new ClassPathResource(datapath);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] attributes = line.split(" ");
//                String firstName = attributes[0];
//                String lastName = attributes[1];
//                String userName = usernameGenerator(firstName, lastName);
//                String password = randomPasswordGenerator();
//                boolean isActive = true;
//                TrainingType specialization = attributes[2];
//                long userId = Long.parseLong(attributes[3]);
//                Trainer trainer = new Trainer(
//                        specialization
//                );
//                trainerMap.put(userId, trainer);
//            }
//        } catch (IOException e) {
//            logger.error("Error initializing trainers: ", e);
//        }
//    }
//    public Map<Long, Trainer> getTrainerMap() {
//        return trainerMap;
//    }
//
//    private String usernameGenerator(String firstName, String lastName) {
//        int counter = 0;
//        StringBuilder builder = new StringBuilder();
//        builder.append(firstName).append(".").append(lastName);
//        List<String> usernames = new ArrayList<>();
//        for (Trainee t : traineeMap.values()) {
//            usernames.add(t.getUser().getUsername());
//        }
//        for (Trainer t : trainerMap.values()) {
//            usernames.add(t.getUser().getUsername());
//        }
//        while (true) {
//            int counterBefore = counter;
//            for (String username : usernames) {
//                if (username.contentEquals(builder)) {
//                    counter++;
//                }
//            }
//            if (counter != 0) {
//                builder.setLength(0);
//                builder.append(firstName);
//                builder.append(".");
//                builder.append(lastName);
//                builder.append(counter);
//            }
//            if (counterBefore == counter) {
//                break;
//            }
//        }
//        logger.info("Generated username");
//        return builder.toString();
//    }
//    private String randomPasswordGenerator() {
//        Random random = new Random();
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 10; i++) {
//            char randomChar = (char) random.nextInt(33, 127);
//            builder.append(randomChar);
//        }
//        logger.info("Generated password");
//        return builder.toString();
//    }
}
