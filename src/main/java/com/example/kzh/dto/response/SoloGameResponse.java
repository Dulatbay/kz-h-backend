package com.example.kzh.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoloGameResponse {
    private String gameId;
    private int totalQuestions;
    private int currentQuestionIndex;
    private SoloGameQuestion currentQuestion;
    private SoloGamePreviousQuestion previousQuestion;
}
