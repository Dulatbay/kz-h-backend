package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "detail_user")
public class DetailUser extends AbstractEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(name = "shok_day")
    private int shokDay;

    @Column(name = "last_played_at")
    private LocalDateTime lastPlayedAt;

    @Column(name = "image_url")
    private String imageUrl;
}

