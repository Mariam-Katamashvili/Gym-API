package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService implements com.mariamkatamashvlii.gym.service.TrainingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeService.class);
    private final TrainingTypeRepository trainingTypeRepo;

    @Autowired
    public TrainingTypeService(TrainingTypeRepository trainingTypeRepo) {
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
