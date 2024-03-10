package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements com.mariamkatamashvlii.gym.service.TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingRepository trainingRepo;

    @Autowired
    public TrainingService(TrainingRepository trainingRepo) {
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
