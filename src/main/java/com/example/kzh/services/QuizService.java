package com.example.kzh.services;

import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {

    Page<QuizResponse> getQuizzes(QuizParams request, User user);

    void create(@Valid QuizCreateRequest quizCreateRequest, User user);

    QuizByIdResponse getQuizById(String id);

    QuizByIdResponse getRandomQuiz();
}

