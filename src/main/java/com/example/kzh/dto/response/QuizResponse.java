package com.example.kzh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private int id;
    private int status;
    private String title;
    private int average;
    private int difficulty;
    private int questionsCount;
}
