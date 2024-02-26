package com.mariamkatamashvlii.gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainee")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "traineeId")
    private Long traineeId;

    @CreationTimestamp
    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    private User user;

    @OneToMany(mappedBy = "trainee")
    private Set<Training> trainingSet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "traineeId"),
            inverseJoinColumns = @JoinColumn(name = "trainerId")
    )
    private Set<Trainer> trainerSet = new HashSet<>();

    public Trainee(Date dob, String address) {
        this.dob = dob;
        this.address = address;
    }
}
