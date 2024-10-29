package com.example.kzh.entities;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "correct_variant_id", nullable = false)
    private Variant correctVariant;

    @Column(name = "level", nullable = false)
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
    private Set<QuizQuestions> questions;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<QuestionVariant> questionVariants;

}

