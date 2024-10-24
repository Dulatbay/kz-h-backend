package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "module")
public class Module extends AbstractEntity<Long> {

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private int level;

    @Column(name = "hours")
    private int hours;

    @Column(name = "number")
    private int number;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private Set<Topic> topics = new HashSet<>();
}
