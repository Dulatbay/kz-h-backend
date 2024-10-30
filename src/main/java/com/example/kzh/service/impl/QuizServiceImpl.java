package com.example.kzh.service.impl;

import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.*;
import com.example.kzh.exception.DbNotFoundException;
import com.example.kzh.repositories.QuestionRepository;
import com.example.kzh.repositories.QuizRepository;
import com.example.kzh.repositories.UserRepository;
import com.example.kzh.repositories.VariantRepository;
import com.example.kzh.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final VariantRepository variantRepository;


    @Override
    public Page<QuizResponse> getQuizzes(QuizParams request, User user) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        Long userId = null;

        if (user != null) userId = user.getId();

        return quizRepository.findAllByFilters(userId, request.getSearchText(), request.getStatus(), request.getDifficulty(), request.getTopics(), pageRequest);
    }

    @Override
    @Transactional
    public void create(QuizCreateRequest quizCreateRequest, User user) {
        Quiz quiz = Quiz.builder()
                .title(quizCreateRequest.getTitle())
                .description(quizCreateRequest.getDescription())
                .showQuestions(quizCreateRequest.isShowQuestions())
                .quizQuestions(new HashSet<>())
                .user(user)
                .build();

        quizCreateRequest.getQuestionCreateRequests()
                .forEach(questionCreateRequest -> {
                    if (questionCreateRequest.isGenerated && questionCreateRequest.questionId == null)
                        throw new IllegalArgumentException("Question id cannot be null");
                    if (!questionCreateRequest.isGenerated && (questionCreateRequest.question == null || questionCreateRequest.question.isBlank()))
                        throw new IllegalArgumentException("Question text cannot be null");


                    Question question;
                    if (questionCreateRequest.isGenerated) {
                        question = questionRepository.findById(questionCreateRequest.questionId)
                                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Question id not found"));

                        QuizQuestions quizQuestions = new QuizQuestions();
                        quizQuestions.setQuiz(quiz);
                        quizQuestions.setQuestion(question);

                        quiz.getQuizQuestions().add(quizQuestions);
                    } else {
                        question = new Question();
                        question.setQuestionText(questionCreateRequest.question);
                        question.setLevel(questionCreateRequest.level);
                        question.setQuestionVariants(new HashSet<>());

                        var correctVariant = variantRepository.findByContent(questionCreateRequest.correctAnswer)
                                .orElse(null);


                        if (correctVariant == null) {
                            correctVariant = variantRepository.save(Variant.builder()
                                    .user(user)
                                    .content(questionCreateRequest.correctAnswer)
                                    .build());
                        }

                        question.setCorrectVariant(correctVariant);
                        question.setUser(user);

                        questionCreateRequest.options.forEach(option -> {
                            var variant = variantRepository.findByContent(option)
                                    .orElse(null);

                            if (variant == null) {
                                variant = variantRepository.save(Variant.builder()
                                        .user(user)
                                        .content(option)
                                        .build());
                            }

                            QuestionVariant questionVariant = new QuestionVariant();
                            questionVariant.setQuestion(question);
                            questionVariant.setVariant(variant);

                            question.getQuestionVariants().add(questionVariant);
                        });

                        QuizQuestions quizQuestions = new QuizQuestions();
                        quizQuestions.setQuiz(quiz);
                        quizQuestions.setQuestion(question);

                        quiz.getQuizQuestions().add(quizQuestions);
                    }
                });


        quizRepository.save(quiz);
    }
}

