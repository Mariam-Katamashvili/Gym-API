package com.mariamkatamashvlii.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainingType")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainingTypeId")
    private Long trainingTypeId;

    @Column(name ="trainingTypeName", nullable = false, unique = true)
    private String trainingTypeName;

    @OneToMany(mappedBy = "trainingType")
    private Set<Training> trainingSet=  new HashSet<>();

    @OneToMany(mappedBy = "specialization")
    private Set<Trainer> trainerSet = new HashSet<>();

    public TrainingType(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }
}
