package com.mariamkatamashvlii.gym.initializer;

import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.Trainer;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class TraineeInit {
//    private static final Logger logger = LoggerFactory.getLogger(TraineeInit.class);
//    private Map<Long, Trainee> traineeMap;
//    private Map<Long, Trainer> trainerMap;
//
//    @Value("${data.file.path.trainee}")
//    private String dataPath;
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
//        logger.info("Initializing trainees from data path: {}", dataPath);
//        ClassPathResource resource = new ClassPathResource(dataPath);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] attributes = line.split(" ");
//                String firstName = attributes[0];
//                String lastName = attributes[1];
//                String userName = usernameGenerator(firstName, lastName);
//                String password = randomPasswordGenerator();
//                boolean isActive = true;
//                String dateString = attributes[2];
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                java.util.Date parseDate = sdf.parse(dateString);
//                Date sqlDate = new Date(parseDate.getTime());
//                String address = attributes[3];
//                long userId = Long.parseLong(attributes[4]);
//                Trainee trainee = new Trainee(
//                        sqlDate,
//                        address
//                );
//                traineeMap.put(userId, trainee);
//            }
//        } catch (IOException e) {
//            logger.error("Error initializing trainees: ", e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Map<Long, Trainee> getTraineeMap() {
//        return traineeMap;
//    }
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
//
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