package com.example.kzh.controller;

import com.example.kzh.constants.Utils;
import com.example.kzh.dto.request.QuizRequest;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.User;
import com.example.kzh.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<QuizResponse>> getQuizzes(@ModelAttribute @Valid QuizRequest quizRequest, Principal principal) {
        Page<QuizResponse> quizResponseDtos = quizService.getQuizzes(quizRequest, principal);

        return ResponseEntity.ok(new PaginatedResponse<>(quizResponseDtos));
    }
}
