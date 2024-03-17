package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainer;
import lombok.Generated;

import java.util.List;

@Generated
public interface TrainerRepository {
    Trainer create(Trainer trainer);

    Trainer update(Trainer trainer);

    Trainer select(String username);

    List<Trainer> findAll();
}
