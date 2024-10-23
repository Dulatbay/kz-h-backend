package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "rating_quiz")
public class RatingQuiz extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "rating", nullable = false)
    private double rating;

}

