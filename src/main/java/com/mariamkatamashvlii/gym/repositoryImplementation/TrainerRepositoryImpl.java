package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.Trainer;
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
public class TrainerRepository implements com.mariamkatamashvlii.gym.repository.TrainerRepository {
    private static final Logger logger = LoggerFactory.getLogger(TrainerRepository.class);
    private final EntityManager entityManager;
    private final UserRepository userRepo;

    @Autowired
    public TrainerRepository(EntityManager entityManager, UserRepository userRepo) {
        this.entityManager = entityManager;
        this.userRepo = userRepo;
        logger.debug("TraineeRepoImpl initialized with EntityManager and UserRepo");
    }

    @Override
    @Transactional
    public void create(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainer);
        logger.info("Created trainer with id {}", trainer.getTrainerId());
    }

    @Override
    @Transactional
    public void update(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainer);
        logger.info("Updated trainer with id {}", trainer.getTrainerId());
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        Trainer trainer = session.get(Trainer.class, id);
        session.remove(trainer);
        logger.info("Deleted trainer with id {}", trainer.getTrainerId());
    }

    @Override
    @Transactional
    public Trainer select(long id) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("Selecting trainer with id {}", id);
        return session.get(Trainer.class, id);
    }

    @Override
    @Transactional
    public Trainer select(String username) {
        User user = userRepo.select(username);
        logger.info("Selecting trainer with username {}", username);
        return user.getTrainer();
    }

    @Override
    @Transactional
    public List<Trainer> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainer> query = session.createQuery("from Trainer ", Trainer.class);
        logger.info("Returning all trainers");
        return query.getResultList();
    }
}
