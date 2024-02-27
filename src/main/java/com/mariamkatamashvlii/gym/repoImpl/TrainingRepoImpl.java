package com.mariamkatamashvlii.gym.repoImpl;

import com.mariamkatamashvlii.gym.repo.TrainingRepo;
import com.mariamkatamashvlii.gym.model.Training;
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
public class TrainingRepoImpl implements TrainingRepo {
    private static final Logger logger = LoggerFactory.getLogger(TrainingRepoImpl.class);
    private EntityManager entityManager;

    @Autowired
    public TrainingRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void create(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(training);
    }

    @Override
    @Transactional
    public void update(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(training);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        Training training = session.get(Training.class, id);
        session.remove(training);
    }

    @Override
    @Transactional
    public Training select(long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Training.class, id);
    }

    @Override
    @Transactional
    public List<Training> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Training> query = session.createQuery("from Training ", Training.class);
        List<Training> trainingList = query.getResultList();
        return trainingList;
    }
}
