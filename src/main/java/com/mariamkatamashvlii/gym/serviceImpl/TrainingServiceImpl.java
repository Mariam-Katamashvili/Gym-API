package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.daoImpl.TrainingDaoImpl;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TrainingServiceImpl implements TrainingService{
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private TrainingDaoImpl trainingDaoImpl;

    @Autowired
    public void setTrainingDaoImpl(TrainingDaoImpl trainingDaoImpl) {
        this.trainingDaoImpl = trainingDaoImpl;
    }

    @Override
    public void create(Training training) {
        logger.info("Creating training with name '{}'", training.getTrainingName());
        trainingDaoImpl.create(training);
    }

    @Override
    public Training select(String trainingName) {
        logger.info("Selecting training with name '{}'", trainingName);
        return trainingDaoImpl.select(trainingName);
    }
}
