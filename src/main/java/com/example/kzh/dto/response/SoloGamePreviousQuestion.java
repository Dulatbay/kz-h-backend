package com.example.kzh.dto.response;

import com.example.kzh.entities.helpers.Variant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SoloGamePreviousQuestion {
    private String quizQuestionId;
    private String question;
    private int questionIdx;
    private List<Variant> variants;
}
