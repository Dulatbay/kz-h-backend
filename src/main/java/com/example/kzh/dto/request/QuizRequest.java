package com.example.kzh.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuizRequest {
    private Integer difficulty;
    private Boolean status;
    private List<Long> topics = new ArrayList<>();
    private String searchText;
    private int page;
    private int size;
}

