package com.example.kzh.entities;

import com.example.kzh.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "_users")
public class User extends AbstractEntity<Long> {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private DetailUser detailUser;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<Question> questions;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<RatingQuiz> ratingQuizzes;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private Set<Quiz> quizzes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<PassedQuiz> passedQuizzes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<PassedQuestion> passedQuestions;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Notes> notes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Variant> variants;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
