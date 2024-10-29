package com.example.kzh.service;

import com.example.kzh.dto.request.QuizRequest;
import com.example.kzh.dto.response.QuizResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface QuizService {

    Page<QuizResponse> getQuizzes(QuizRequest request, Principal principal);
}

