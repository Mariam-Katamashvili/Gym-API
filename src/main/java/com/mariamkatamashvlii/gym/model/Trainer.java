package com.mariamkatamashvlii.gym.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trainer")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainerId")
    private Long trainerId;

    @ManyToOne
    @JoinColumn(name = "specialization", referencedColumnName = "trainingTypeId")
    private TrainingType specialization;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    private User user;

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainingSet = new HashSet<>();

    @ManyToMany(mappedBy = "trainerList")
    private List<Trainee> traineeList = new ArrayList<>();

    public Trainer(TrainingType specialization) {
        this.specialization = specialization;
    }
}
