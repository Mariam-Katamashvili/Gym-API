package com.mariamkatamashvlii.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "training")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainingId")
    private Long trainingId;

    @ManyToOne
    @JoinColumn(name = "traineeId", referencedColumnName = "traineeId")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainerId", referencedColumnName = "trainerId")
    private Trainer trainer;

    @Column(name = "trainingName", nullable = false, unique = true)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "trainingTypeId", referencedColumnName = "trainingTypeId")
    private TrainingType trainingType;

    @Column(name = "trainingDate", nullable = false)
    private Date trainingDate;

    @Column(name = "duration", nullable = false)
    private Number duration;

    public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, Date trainingDate, Number duration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }
}
