package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "topic")
public class Topic extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "number")
    private int number;

    @Column(name = "percentage")
    private double percentage;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Notes> notes = new HashSet<>();

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Term> terms;

    @OneToOne(mappedBy = "lastTopic",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            optional = false)
    private DetailUser detailUser;

}



