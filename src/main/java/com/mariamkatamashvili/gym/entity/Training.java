package com.mariamkatamashvili.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id", referencedColumnName = "id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    private Trainer trainer;

    @Column(nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_type_id", referencedColumnName = "id")
    private TrainingType trainingType;

    @Column(nullable = false)
    private LocalDate trainingDate;

    @Column(nullable = false)
    private Integer duration;
}