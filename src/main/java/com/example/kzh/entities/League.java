package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "league")
public class League extends AbstractEntity<Long> {

    @Column(name = "def", nullable = false, unique = true)
    private String def;

    @Column(name = "number", nullable = false, unique = true)
    private int number;

    @OneToOne(mappedBy = "league",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            optional = false)
    private DetailUser detailUser;
}
