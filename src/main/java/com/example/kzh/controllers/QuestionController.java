package com.example.kzh.controllers;

import com.example.kzh.dto.params.PaginationParams;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.dto.response.QuestionResponse;
import com.example.kzh.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<QuestionResponse>> getQuestions(@Valid
                                                                            @ModelAttribute
                                                                            PaginationParams paginationParams) {
        return ResponseEntity.ok(questionService.getQuestions(paginationParams));
    }
}
