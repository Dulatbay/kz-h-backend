package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "question")
public class Question extends AbstractEntity<Long> {

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "has_image")
    private boolean hasImage;

    @Column(name = "correct_variant_index", nullable = false)
    private int correctVariantIndex;

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
            fetch = FetchType.LAZY)
    private Set<QuestionTerm> questionTerm;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY)
    private Set<QuestionImages> questionImages;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY)
    private Set<PassedQuestion> passedQuestions;

    @OneToMany(mappedBy = "question",
        fetch = FetchType.LAZY)
    private Set<QuizQuestions> questions;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY)
    private Set<QuestionVariant> questionVariants;

    public Question(String content, boolean hasImage, int correctVariantIndex, int level, boolean isVerified, boolean hasTerm, User user, Topic topic) {
        this.content = content;
        this.hasImage = hasImage;
        this.correctVariantIndex = correctVariantIndex;
        this.level = level;
        this.isVerified = isVerified;
        this.hasTerm = hasTerm;
        this.user = user;
        this.topic = topic;
    }
}

