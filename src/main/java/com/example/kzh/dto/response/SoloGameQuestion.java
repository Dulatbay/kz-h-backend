package com.example.kzh.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SoloGameQuestion {
    private String quizQuestionId;
    private String question;
    private int questionIdx;
    private int duration;
    private List<String> variants;
}
