package com.example.kzh.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "archive_books")
public class ArchiveBooks extends AbstractEntity<Long> {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "file_uri", nullable = false)
    private String fileUri;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "downloads_count", nullable = false)
    private int downloadsCount;
}

