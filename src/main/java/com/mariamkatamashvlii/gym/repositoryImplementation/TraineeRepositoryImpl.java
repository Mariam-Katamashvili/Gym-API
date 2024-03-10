package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
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
public class TraineeRepository implements com.mariamkatamashvlii.gym.repository.TraineeRepository {
    private static final Logger logger = LoggerFactory.getLogger(TraineeRepository.class);
    private final EntityManager entityManager;
    private final UserRepository userRepo;

    @Autowired
    public TraineeRepository(EntityManager entityManager, UserRepository userRepo) {
        this.entityManager = entityManager;
        this.userRepo = userRepo;
        logger.debug("TraineeRepoImpl initialized with EntityManager and UserRepo");
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
        logger.info("Removed trainee with id {}", trainee.getTraineeId());
    }

    @Override
    @Transactional
    public void delete(String username) {
        userRepo.delete(username);
        logger.info("Removed trainee with username {}", username);
    }

    @Override
    @Transactional
    public Trainee select(long id) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("Selecting trainee with id {}", id);
        return session.get(Trainee.class, id);
    }

    @Override
    @Transactional
    public Trainee select(String username) {
        User user = userRepo.select(username);
        logger.info("Selecting trainee with username {}", username);
        return user.getTrainee();
    }

    @Override
    @Transactional
    public List<Trainee> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainee> query = session.createQuery("from Trainee", Trainee.class);
        logger.info("Returning all trainees");
        return query.getResultList();
    }
}