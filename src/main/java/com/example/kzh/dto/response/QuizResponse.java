package com.example.kzh.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private Long id;
    private Boolean status;
    private String title;
    private int average;
    private int difficulty;
    private Long questions;
}

