package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @Column(name = "show_questions", nullable = false)
    private boolean showQuestions;

    @Column(name = "duration", nullable = false)
    private int duration;

    // todo: trigger
    @Column(name = "level", nullable = false)
    private double level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RatingQuiz> ratingQuizzes;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizQuestions> quizQuestions;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PassedQuiz> passedQuizzes;
}

