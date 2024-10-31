package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "passed_question")
public class PassedQuestion extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_variant_id", nullable = false)
    private Variant selectedVariant;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "is_quiz", nullable = false)
    private boolean isQuiz;

    @OneToMany(mappedBy = "passedQuestion", fetch = FetchType.LAZY)
    private Set<PassedQuizQuestion> passedQuizQuestions;
}
