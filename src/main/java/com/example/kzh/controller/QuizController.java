package com.example.kzh.controller;

import com.example.kzh.constants.Utils;
import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<QuizResponse>> getQuizzes(@ModelAttribute @Valid QuizParams quizParams) {
        var userDetails = Utils.getUserDetails();

        Page<QuizResponse> quizResponseDtos = quizService.getQuizzes(quizParams, userDetails);

        return ResponseEntity.ok(new PaginatedResponse<>(quizResponseDtos));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('quiz:create')")
    public ResponseEntity<Void> createQuiz(@RequestBody @Valid QuizCreateRequest quizCreateRequest) {
        var userDetails = Utils.getUserDetails();

        quizService.create(quizCreateRequest, userDetails);

        return ResponseEntity.status(201).build();
    }
}
