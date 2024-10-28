package com.example.kzh.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizResponseDto {
    private int status;
    private String title;
    private int average;
    private int difficulty;
    private int questionsCount;
}
