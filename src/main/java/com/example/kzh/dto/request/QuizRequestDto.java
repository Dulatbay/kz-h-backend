package com.example.kzh.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuizRequestDto {
    private Integer difficulty;
    private Boolean status;
    private List<Integer> topics;
    private List<Integer> modules;
    private Integer page;
    private Integer size;
    private String searchText;
}

