package com.example.kzh.mappers;

import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.entities.Quiz;
import com.example.kzh.entities.User;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {
    public Quiz mapFromCreateRequest(QuizCreateRequest quizCreateRequest, User user) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateRequest.getTitle());
        quiz.setDescription(quizCreateRequest.getDescription());
        quiz.setVerified(false);
        quiz.setShowQuestions(quizCreateRequest.isShowQuestions());
        quiz.setLanguage(quizCreateRequest.getLanguage());
        quiz.setUser(user);
        return quiz;
    }
}
