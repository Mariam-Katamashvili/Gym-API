package com.mariamkatamashvlii.gym.repoImpl;

import com.mariamkatamashvlii.gym.repo.TraineeRepo;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.User;
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
public class TraineeRepoImpl implements TraineeRepo {
    private static final Logger logger = LoggerFactory.getLogger(TraineeRepoImpl.class);
    private EntityManager entityManager;
    private UserRepo userRepo;

    @Autowired
    public TraineeRepoImpl(EntityManager entityManager, UserRepo userRepo) {
        this.entityManager = entityManager;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void create(Trainee trainee) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainee);
        logger.info("Created trainee with id {}", trainee.getTraineeId());
    }

    @Override
    @Transactional
    public void update(Trainee trainee) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainee);
        logger.info("Updated trainee with id {}", trainee.getTraineeId());
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        Trainee trainee = session.get(Trainee.class, id);
        session.remove(trainee);
    }

    @Override
    @Transactional
    public void delete(String username) {
        userRepo.delete(username);
    }

    @Override
    @Transactional
    public Trainee select(long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Trainee.class, id);
    }

    @Override
    @Transactional
    public Trainee select(String username) {
        User user = userRepo.select(username);
        return user.getTrainee();
    }

    @Override
    @Transactional
    public List<Trainee> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainee> query = session.createQuery("from Trainee", Trainee.class);
        List<Trainee> traineeList = query.getResultList();
        return traineeList;
    }
}