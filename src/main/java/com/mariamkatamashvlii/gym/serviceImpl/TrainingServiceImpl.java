package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.TrainingRepo;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainingRepo trainingRepo;

    @Autowired
    public TrainingServiceImpl(TrainingRepo trainingRepo) {
        this.trainingRepo = trainingRepo;
        logger.debug("TrainingServiceImpl initialized with TrainingRepo");
    }

    @Override
    public void create(Training training) {
        trainingRepo.create(training);
        logger.info("Created training with id {}", training.getTrainingId());
    }

    @Override
    public void update(Training training) {
        trainingRepo.update(training);
        logger.info("Updated training with id {}", training.getTrainingId());
    }

    @Override
    public void delete(long id) {
        trainingRepo.delete(id);
        logger.info("Deleted training with id {}", id);
    }

    @Override
    public Training select(long id) {
        logger.info("Selecting training with id {}", id);
        return trainingRepo.select(id);
    }

    @Override
    public List<Training> findAll() {
        logger.info("Returning all trainings");
        return trainingRepo.findAll();
    }
}
