package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "passed_quiz_question")
public class PassedQuizQuestion extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passed_question_id", nullable = false)
    private PassedQuestion passedQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passed_quiz_id", nullable = false)
    private PassedQuiz passedQuiz;
}

