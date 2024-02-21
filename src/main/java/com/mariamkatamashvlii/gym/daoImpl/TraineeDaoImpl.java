package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TraineeDao;
import com.mariamkatamashvlii.gym.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);
    private Map<Long, Trainee> traineeMap;

    @Autowired
    public void setTraineeMap(Map<Long, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
    }

    public void create(Trainee trainee) {
        long id = trainee.getUserId();
        if (traineeMap.containsKey(id)) {
            logger.error("Trainee with ID {} already exists!", id);
            return;
        }
        traineeMap.put(id, trainee);
        logger.info("Trainee with ID {} created successfully!", id);
    }

    public void update(long id, Trainee trainee) {
        if (!traineeMap.containsKey(id)) {
            logger.info("Trainee with ID {} does not exist, creating a new one.", id);
            create(trainee);
        } else {
            traineeMap.put(id, trainee);
            logger.info("Trainee with ID {} updated successfully!", id);
        }
    }

    public void delete(long id) {
        if (!traineeMap.containsKey(id)) {
            logger.error("Trainee with ID {} does not exist!", id);
            return;
        }
        traineeMap.remove(id);
        logger.info("Trainee with ID {} deleted!", id);
    }

    public Trainee select(long id) {
        if (!traineeMap.containsKey(id)) {
            logger.error("Trainee with ID {} does not exist!", id);
            return null;
        }
        return traineeMap.get(id);
    }

}