package com.mariamkatamashvlii.gym.initializer;

import com.mariamkatamashvlii.gym.model.Training;
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
import java.time.LocalDate;
import java.util.Map;

@Component
public class TrainingInit {
    private static final Logger logger = LoggerFactory.getLogger(TrainingInit.class);
    private Map<String,Training> trainingMap;

    @Value("${data.file.path.training}")
    private String datapath;

    @Autowired
    public void setTrainingMap(Map<String, Training> trainingMap) {
        this.trainingMap = trainingMap;
    }
    @PostConstruct
    public void initializer() {
        logger.info("Initializing trainings from data path: {}", datapath);
        ClassPathResource resource = new ClassPathResource(datapath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] attributes = line.split(" ");
                long traineeId = Long.parseLong(attributes[0]);
                long trainerId = Long.parseLong(attributes[1]);
                String trainingName = attributes[2];
                TrainingType trainingType = new TrainingType(attributes[3]);
                LocalDate trainingDate = LocalDate.parse(attributes[4]);
                float duration = Float.parseFloat(attributes[5]);
                Training training = new Training(
                        traineeId,
                        trainerId,
                        trainingName,
                        trainingType,
                        trainingDate,
                        duration
                );
                trainingMap.put(trainingName, training);
            }
        } catch (IOException e) {
            logger.error("Error initializing trainings: ", e);
        }
    }

    public Map<String, Training> getTrainingMap() {
        return trainingMap;
    }
}
