package com.mariamkatamashvlii.gym.repository.implementation;

import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Training create(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(training);
        log.info("Created training with id {}", training.getId());
        return training;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        Training training = session.get(Training.class, id);
        session.remove(training);
        log.info("Deleted training with id {}", training.getId());
    }

    @Override
    @Transactional
    public Training select(long id) {
        Session session = entityManager.unwrap(Session.class);
        log.info("Selecting training with id {}", id);
        return session.get(Training.class, id);
    }

}
