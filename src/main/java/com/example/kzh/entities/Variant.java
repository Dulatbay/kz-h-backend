package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "variant")
public class Variant extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false, unique = true)
    private String content;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private Set<QuestionVariant> questionVariants;
}