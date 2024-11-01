package com.example.kzh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question extends AbstractEntity<Long> {

    @Column(name = "text", nullable = false)
    private String questionText;

    @Column(name = "has_image")
    private boolean hasImage;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuestionCorrectVariant> questionCorrectVariant;

    @Column(name = "level", nullable = false)
    @Min(value = 1, message = "Level must be more than 0")
    @Max(value = 3, message = "Level must be smaller than 3")
    private int level;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "has_term")
    private boolean hasTerm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<QuestionTerm> questionTerm;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<QuestionImages> questionImages;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<PassedQuestion> passedQuestions;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<QuizQuestion> questions;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Variant> variants;

}

