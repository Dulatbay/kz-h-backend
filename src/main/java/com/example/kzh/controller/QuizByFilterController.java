package com.example.kzh.controller;

import com.example.kzh.dto.request.QuizRequestDto;
import com.example.kzh.dto.response.QuizResponseDto;
import com.example.kzh.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizes")
@Slf4j
public class QuizByFilterController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<QuizResponseDto> getQuizByFilter(@RequestBody @Valid QuizRequestDto quizRequestDto){
        return ResponseEntity.ok(quizService.getQuizzes(quizRequestDto));
    }
}
