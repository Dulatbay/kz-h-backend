package com.example.kzh.dto.request;

import com.example.kzh.entities.enums.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    @NotNull
    private Language language;

    @Size(min = 2)
    private List<@Valid QuestionCreateRequest> questionCreateRequests = new ArrayList<>();

    public static class QuestionCreateRequest {
        public boolean isGenerated;
        public Long questionId;
        public String question;
        public Long topicId;

        @NotNull
        public Integer level;

        @NotNull
        @Min(5)
        @Min(300)
        public Short durationInSeconds;

        @Size(min = 1)
        @UniqueElements
        @NotNull
        public Set<@NotBlank String> correctAnswers;

        @Size(min = 2)
        @UniqueElements
        public List<@NotBlank String> options;
    }
}
