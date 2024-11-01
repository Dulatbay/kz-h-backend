package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Question_correct_variant", uniqueConstraints = {@UniqueConstraint(columnNames = {"variant_id", "question_id"})})
public class QuestionCorrectVariant extends AbstractEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;
}

