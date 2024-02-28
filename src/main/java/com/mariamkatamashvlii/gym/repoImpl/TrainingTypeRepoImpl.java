package com.mariamkatamashvlii.gym.repoImpl;

import com.mariamkatamashvlii.gym.repo.TrainingTypeRepo;
import com.mariamkatamashvlii.gym.model.TrainingType;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TrainingTypeRepoImpl implements TrainingTypeRepo {
    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeRepoImpl.class);
    private final EntityManager entityManager;

    @Autowired
    public TrainingTypeRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        logger.debug("TrainingTypeRepoImpl initialized with EntityManager");
    }

    @Override
    @Transactional
    public TrainingType select(long id) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("Selecting training type with id {}", id);
        return session.get(TrainingType.class, id);

    }

    @Override
    @Transactional
    public List<TrainingType> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<TrainingType> query = session.createQuery("from TrainingType ", TrainingType.class);
        List<TrainingType> trainingTypeList = query.getResultList();
        logger.info("Returning all trainingTypes");
        return trainingTypeList;
    }
}
