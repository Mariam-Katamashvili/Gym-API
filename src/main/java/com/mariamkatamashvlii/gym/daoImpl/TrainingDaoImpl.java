package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TrainingDao;
import com.mariamkatamashvlii.gym.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private Map<String, Training> trainingMap;

    @Autowired
    public void setTrainingMap(Map<String, Training> trainingMap) {
        this.trainingMap = trainingMap;
    }

    @Override
    public void create(Training training) {
        String trainingName = training.getTrainingName();
        if (trainingMap.containsKey(trainingName)) {
            logger.error("Training with name {} already exists!", trainingName);
            return;
        }
        trainingMap.put(trainingName, training);
        logger.info("Training with name {} created successfully!", trainingName);
    }

    @Override
    public Training select(String trainingName) {
        if(!trainingMap.containsKey(trainingName)) {
            logger.error("Training with name {} does not exist!", trainingName);
            return null;
        }
        return trainingMap.get(trainingName);
    }

}
