package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "quiz")
public class Quiz extends AbstractEntity<Long> {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "has_image", nullable = false)
    private boolean hasImage;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "duration", nullable = false)
    private int duration;

    // todo: trigger
    @Column(name = "level", nullable = false)
    private double level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private Set<RatingQuiz> ratingQuizzes;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private Set<QuizQuestions> quizQuestions;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private Set<PassedQuiz> passedQuizzes;



}

