package com.example.kzh.mappers;

import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.entities.Question;
import com.example.kzh.entities.Quiz;
import com.example.kzh.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public QuizByIdResponse mapFromEntityById(Quiz randomQuiz, List<Question> questions) {
        QuizByIdResponse quizByIdResponse = new QuizByIdResponse();
        quizByIdResponse.setId(randomQuiz.getId());
        quizByIdResponse.setDescription(randomQuiz.getDescription());
        quizByIdResponse.setQuestionsCount(questions.size());
        quizByIdResponse.setTitle(randomQuiz.getTitle());

        if (randomQuiz.isShowQuestions()) {
            List<String> questionsTexts = questions.stream().map(Question::getQuestionText).toList();
            quizByIdResponse.setQuestions(questionsTexts);
        }

        return quizByIdResponse;
    }
}
