package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.TrainingTypeRepo;
import com.mariamkatamashvlii.gym.model.TrainingType;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);
    private final TrainingTypeRepo trainingTypeRepo;

    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeRepo trainingTypeRepo) {
        this.trainingTypeRepo = trainingTypeRepo;
        logger.debug("TrainingTypeService initialized with TrainingTypeRepo");
    }

    @Override
    public TrainingType select(long id) {
        logger.info("Selecting TrainingType with id {}", id);
        return trainingTypeRepo.select(id);
    }

    @Override
    public List<TrainingType> findAll() {
        logger.info("Returning all TrainingTypes");
        return trainingTypeRepo.findAll();
    }
}
