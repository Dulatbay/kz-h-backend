package com.example.kzh.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class QuizCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;
    private boolean showQuestions;

    @Size(min = 2)
    private List<@Valid QuestionCreateRequest> questionCreateRequests = new ArrayList<>();


    public static class QuestionCreateRequest {
        public boolean isGenerated;
        public Long questionId;
        public String question;

        @NotNull
        public Integer level;

        @NotBlank
        public String correctAnswer;

        @Size(min = 2)
        @UniqueElements
        public List<@NotBlank String> options;
    }
}
