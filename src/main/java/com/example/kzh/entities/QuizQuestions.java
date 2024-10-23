package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "quiz_questions")
public class QuizQuestions extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
