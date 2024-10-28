package com.example.kzh.service;

import com.example.kzh.dto.request.QuizRequestDto;
import com.example.kzh.dto.response.QuizResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {

    QuizResponseDto getQuizzes(QuizRequestDto request);
}

