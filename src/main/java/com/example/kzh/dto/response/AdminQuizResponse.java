package com.example.kzh.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminQuizResponse extends QuizResponse {
    private String author;
    private boolean isVerified;
}

