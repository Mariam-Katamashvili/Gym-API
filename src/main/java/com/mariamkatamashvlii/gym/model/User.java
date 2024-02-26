package com.mariamkatamashvlii.gym.model;

import com.mariamkatamashvlii.gym.initializer.PasswordGenerator;
import com.mariamkatamashvlii.gym.initializer.UsernameGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Trainee trainee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Trainer trainer;

    public User(String firstName, String lastName, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = UsernameGenerator.usernameGenerator(firstName, lastName);
        this.password = PasswordGenerator.randomPasswordGenerator();
        this.isActive = isActive;
    }

}