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

    @Column(name = "is_duration_per_question", nullable = false)
    private boolean isDurationPerQuestion;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "is_immediately_results", nullable = false)
    private boolean isImmediatelyResults;

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

