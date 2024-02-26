package com.mariamkatamashvlii.gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainer")
@Getter
@Setter
@NoArgsConstructor
@ToString
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

    @ManyToMany(mappedBy = "trainerSet")
    private Set<Trainee> traineeSet = new HashSet<>();

    public Trainer(TrainingType specialization) {
        this.specialization = specialization;
    }
}
