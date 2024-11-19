package com.example.kzh.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SoloGameResultResponse {
    private String gameId;
    private String quizId;
    private String quizName;
    private long duration;
    private double result;
    private double beats;
    private double record;

    private List<AnsweredQuestion> answeredQuestions;


    @Data
    public static class AnsweredQuestion {
        private String question;
        private String questionId;
        private List<AnsweredVariant> variants;
    }

    @Data
    public static class AnsweredVariant {
        private boolean isCorrect;
        private boolean isChosen;
        private String answer;
    }
}
