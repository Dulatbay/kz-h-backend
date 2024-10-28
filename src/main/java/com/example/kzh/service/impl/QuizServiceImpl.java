package com.example.kzh.service.impl;

import com.example.kzh.dto.request.QuizRequestDto;
import com.example.kzh.dto.response.QuizResponseDto;
import com.example.kzh.mappers.QuizMapper;
import com.example.kzh.repositories.QuizRepository;
import com.example.kzh.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;


    @Override
    public QuizResponseDto getQuizzes(QuizRequestDto request) {
        return QuizResponseDto.builder().build();
    }
}

