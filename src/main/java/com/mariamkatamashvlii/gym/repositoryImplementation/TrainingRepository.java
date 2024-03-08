package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.Training;
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
public class TrainingRepository implements com.mariamkatamashvlii.gym.repository.TrainingRepository {
    private static final Logger logger = LoggerFactory.getLogger(TrainingRepository.class);
    private final EntityManager entityManager;

    @Autowired
    public TrainingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        logger.debug("TrainingRepoImpl initialized with EntityManager");
    }

    @Override
    @Transactional
    public void create(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(training);
        logger.info("Created training with id {}", training.getTrainingId());
    }

    @Override
    @Transactional
    public void update(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(training);
        logger.info("Updated training with id {}", training.getTrainingId());
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        Training training = session.get(Training.class, id);
        session.remove(training);
        logger.info("Deleted training with id {}", training.getTrainingId());
    }

    @Override
    @Transactional
    public Training select(long id) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("Selecting training with id {}", id);
        return session.get(Training.class, id);
    }

    @Override
    @Transactional
    public List<Training> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Training> query = session.createQuery("from Training ", Training.class);
        logger.info("Returning all trainings");
        return query.getResultList();
    }
}
