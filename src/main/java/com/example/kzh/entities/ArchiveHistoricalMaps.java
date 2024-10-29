package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "archive_historic_amaps")
public class ArchiveHistoricalMaps extends AbstractEntity<Long> {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "file_uri", nullable = false)
    private String fileUri;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "downloads_count", nullable = false)
    private int downloadsCount;
}

