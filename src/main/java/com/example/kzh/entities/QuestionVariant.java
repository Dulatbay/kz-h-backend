package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "question_variants")
public class QuestionVariant extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

}

