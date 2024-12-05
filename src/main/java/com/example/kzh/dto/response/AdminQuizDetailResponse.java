package com.example.kzh.dto.response;

import com.example.kzh.entities.helpers.Variant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminQuizDetailResponse {
    private String quizId;
    private String quizName;
    private String quizDescription;
    private List<Question> questions = new ArrayList<>();
    private boolean isVerified;
    private String verifiedBy;

    @Data
    public static class Question {
        private String id;
        private String content;
        private boolean isVerified; // quizQuestion verified
        private boolean isBaseVerified; // question verified
        private List<Variant> variants = new ArrayList<>();
    }
}
