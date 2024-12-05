package com.example.kzh.services;

import com.example.kzh.dto.params.AdminQuizSearchParams;
import com.example.kzh.dto.params.QuizSearchParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.request.VerifyQuizRequest;
import com.example.kzh.dto.response.AdminQuizDetailResponse;
import com.example.kzh.dto.response.AdminQuizResponse;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {
    Page<QuizResponse> getQuizzes(QuizSearchParams request, User user);

    void create(@Valid QuizCreateRequest quizCreateRequest, User user);

    QuizByIdResponse getQuizById(String id);

    QuizByIdResponse getRandomQuiz();

    void verifyFull(User userDetails, String quizId);

    Page<AdminQuizResponse> getAdminQuizzes(AdminQuizSearchParams quizSearchParams);

    void verifyPartial(User userDetails, String quizId, VerifyQuizRequest verifyQuizRequest);

    AdminQuizDetailResponse getAdminQuizById(String quizId);
}

