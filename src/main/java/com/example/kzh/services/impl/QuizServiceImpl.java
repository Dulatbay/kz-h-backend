package com.example.kzh.services.impl;

import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.*;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.mappers.QuestionMapper;
import com.example.kzh.mappers.QuizMapper;
import com.example.kzh.mappers.VariantMapper;
import com.example.kzh.repositories.*;
import com.example.kzh.services.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final VariantMapper variantMapper;
    private final QuizQuestionsRepository quizQuestionsRepository;


    @Override
    public Page<QuizResponse> getQuizzes(QuizParams request, User user) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        Long userId = null;

        if (user != null) userId = user.getId();

        return quizRepository.findAllByFilters(userId, request.getSearchText(), request.getStatus(), request.getDifficulty(), request.getTopics(), pageRequest);
    }

    @Override
    @Transactional
    public void create(@Valid QuizCreateRequest quizCreateRequest, User user) {
        Quiz quiz = quizMapper.mapFromCreateRequest(quizCreateRequest, user);

        var quizQuestions = quizCreateRequest.getQuestionCreateRequests()
                .stream()
                .map(questionCreateRequest -> {
                    var question = getQuestion(questionCreateRequest, user);

                    QuizQuestion quizQuestion = new QuizQuestion();
                    quizQuestion.setQuestion(question);
                    quizQuestion.setQuiz(quiz);

                    return quizQuestion;
                })
                .collect(Collectors.toSet());


        quizRepository.save(quiz);
        quizQuestionsRepository.saveAll(quizQuestions);
    }

    private Question getQuestion(QuizCreateRequest.QuestionCreateRequest questionToCreate, User user) {
        if (questionToCreate.isGenerated) {
            return questionRepository.findById(questionToCreate.questionId)
                    .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Question not found"));
        }

        var question = questionMapper.mapFromCreateRequest(questionToCreate, user);

        var variants = questionToCreate.options
                .stream()
                .map(option -> variantMapper.mapFromCreateRequest(option, user))
                .peek(variant -> variant.setQuestion(question))
                .collect(Collectors.toSet());


        Set<QuestionCorrectVariant> correctVariant = variants
                .stream()
                .filter(i -> questionToCreate.correctAnswers.contains(i.getContent()))
                .map(i -> {
                    QuestionCorrectVariant questionCorrectVariant = new QuestionCorrectVariant();
                    questionCorrectVariant.setQuestion(question);
                    questionCorrectVariant.setVariant(i);
                    return questionCorrectVariant;
                })
                .collect(Collectors.toSet());

        if (correctVariant.isEmpty())
            throw new IllegalArgumentException("Correct is incorrect");

        question.setQuestionCorrectVariant(correctVariant);

        question.setVariants(variants);

        return question;
    }

    @Override
    public QuizByIdResponse getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));

        List<Question> questions = questionRepository.findAllQuestionsByQuizId(id);

        return quizMapper.mapFromEntityById(quiz, questions);
    }

    @Override
    public QuizByIdResponse getRandomQuiz() {
        var randomQuiz = quizRepository.findRandomQuiz()
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Quiz not found"));
        var questions = questionRepository.findAllQuestionsByQuizId(randomQuiz.getId());

        return quizMapper.mapFromEntityById(randomQuiz, questions);


    }





}

