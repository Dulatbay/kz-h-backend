package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "passed_quiz")
public class PassedQuiz extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "passedQuiz", fetch = FetchType.LAZY)
    private Set<PassedQuizQuestion> passedQuizQuestions;
}

