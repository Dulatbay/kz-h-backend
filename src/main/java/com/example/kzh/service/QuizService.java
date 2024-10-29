package com.example.kzh.service;

import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {

    Page<QuizResponse> getQuizzes(QuizParams request, User user);

    void create(QuizCreateRequest quizCreateRequest, User user);
}

