package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "variant")
public class Variant extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false, unique = true)
    private String content;

    @Column(name = "is_verified", nullable = false)
    @ColumnDefault("false")
    private boolean isVerified;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private Set<QuestionVariant> questionVariants;
}