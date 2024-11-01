package com.example.kzh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_question", uniqueConstraints = {@UniqueConstraint(columnNames = {"quiz_id", "question_id"})})
public class QuizQuestion extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "duration_in_seconds",nullable = false)
    @Min(5)
    @Min(300)
    private Short durationInSeconds;
}
