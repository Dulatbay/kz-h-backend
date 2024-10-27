package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "passed_question")
public class PassedQuestion extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_index", nullable = false)
    private int selectedIndex;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "is_quiz", nullable = false)
    private boolean isQuiz;

    @OneToMany(mappedBy = "passedQuestion", fetch = FetchType.LAZY)
    private Set<PassedQuizQuestion> passedQuizQuestions;
}
