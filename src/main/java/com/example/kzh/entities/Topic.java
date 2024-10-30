package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "topic")
public class Topic extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "number", nullable = false)
    private int number;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Notes> notes = new HashSet<>();

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Term> terms;
}



