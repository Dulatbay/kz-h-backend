package com.example.kzh.dto.request;

import com.example.kzh.config.validators.EitherOr;
import com.example.kzh.entities.enums.Language;
import com.example.kzh.entities.enums.Level;
import com.example.kzh.entities.helpers.Variant;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
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

    @Getter
    @EitherOr
    public static class QuestionCreateRequest {
        @Valid QuestionGenerate questionGenerate;
        @Valid QuestionCreate questionCreate;
    }

    @Getter
    public static class QuestionGenerate {
        private String questionId;
        private Integer durationInSeconds;

        @Size(min = 2)
        @UniqueElements
        private List<@NotNull Variant> variants;
    }

    @Getter
    public static class QuestionCreate {
        private String question;
        private Set<String> topicId;

        @NotNull
        private Level level;
        private Integer durationInSeconds;

        @Size(min = 2)
        @UniqueElements
        private List<@NotNull Variant> variants;
    }
}
