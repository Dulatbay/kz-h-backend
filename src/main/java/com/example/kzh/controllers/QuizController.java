package com.example.kzh.controllers;

import com.example.kzh.constants.Utils;
import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.services.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<QuizResponse>> getQuizzes(@ModelAttribute @Valid QuizParams quizParams) {
        var userDetails = Utils.getCurrentUser();

        Page<QuizResponse> quizResponseDtos = quizService.getQuizzes(quizParams, userDetails);

        return ResponseEntity.ok(new PaginatedResponse<>(quizResponseDtos));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('quiz:create')")
    public ResponseEntity<Void> createQuiz(@RequestBody QuizCreateRequest quizCreateRequest) {
        var currentUser = Utils.getCurrentUser();

        quizService.create(quizCreateRequest, currentUser);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizByIdResponse> getQuizById(@PathVariable Long id){
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("/random-one")
    public ResponseEntity<QuizByIdResponse> getRandomQuiz(){
        return ResponseEntity.ok(quizService.getRandomQuiz());
    }
}