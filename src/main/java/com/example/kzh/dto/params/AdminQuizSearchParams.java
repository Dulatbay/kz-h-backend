package com.example.kzh.dto.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminQuizSearchParams extends QuizSearchParams {
    private String authorName;
    private Boolean verified;
}
