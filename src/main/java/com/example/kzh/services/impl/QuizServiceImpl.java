package com.example.kzh.services.impl;

import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.*;
import com.example.kzh.entities.helpers.QuizQuestion;
import com.example.kzh.entities.helpers.Variant;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.mappers.QuizMapper;
import com.example.kzh.repositories.QuestionRepository;
import com.example.kzh.repositories.QuizRepository;
import com.example.kzh.repositories.TopicRepository;
import com.example.kzh.services.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final MongoTemplate mongoTemplate;
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    @Override
    public Page<QuizResponse> getQuizzes(QuizParams request, User user) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Query query = new Query().with(pageable);

        if (request.getLevel() != null) {
            query.addCriteria(Criteria.where("level").is(request.getLevel()));
        }


        if (request.getTopics() != null && !request.getTopics().isEmpty()) {
            query.addCriteria(Criteria.where("topics").in(request.getTopics()));
        }

        if (request.getSearchText() != null && !request.getSearchText().isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(request.getSearchText(), "i"));
        }

        List<Quiz> quizzes = mongoTemplate.find(query, Quiz.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), Quiz.class);

        List<QuizResponse> quizResponses = quizzes.stream()
                .map(quizMapper::toQuizResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(quizResponses, pageable, count);
    }

    @Override
    @Transactional
    public void create(@Valid QuizCreateRequest quizCreateRequest, User user) {
        Quiz quiz = quizMapper.toQuizEntity(quizCreateRequest);
        quiz.setAuthor(user);

        var questionToCreate = quizCreateRequest.getQuestionCreateRequests();

        questionToCreate.forEach(questionRequest -> {
            var questionGenerateReq = questionRequest.getQuestionGenerate();
            var questionCreateReq = questionRequest.getQuestionCreate();

            boolean isGenerate = questionRequest.getQuestionGenerate() != null;

            QuizQuestion quizQuestion;
            if (isGenerate) {
                var question = generateQuestion(questionGenerateReq);

                questionGenerateReq.getVariants().addAll(question.getVariants());

                quizQuestion = createQuizQuestion(question,
                        questionGenerateReq.getVariants(),
                        questionGenerateReq.getDurationInSeconds()
                );
            } else {
                var question = createQuestion(questionCreateReq);

                quizQuestion = createQuizQuestion(question,
                        questionCreateReq.getVariants(),
                        questionCreateReq.getDurationInSeconds());
            }

            quiz.getQuestionsWithDuration().add(quizQuestion);
        });

        quizRepository.save(quiz);
        log.info("Quiz created with ID: {}", quiz.getId());
    }

    private QuizQuestion createQuizQuestion(Question question, Set<Variant> variants, int duration) {
        return new QuizQuestion(question, variants, duration);
    }


    private Question generateQuestion(@Valid QuizCreateRequest.QuestionGenerate questionGenerate) {
        var questionId = questionGenerate.getQuestionId();

        return questionRepository.findById(questionId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Question not found"));
    }

    // todo: transform to mapstruct
    private Question createQuestion(@Valid QuizCreateRequest.QuestionCreate questionCreate) {
        Question question = new Question();
        question.setContent(questionCreate.getQuestion());
        question.setLevel(questionCreate.getLevel());
        question.setTopics(topicRepository.findByIdIn(questionCreate.getTopicId()));
        question.setVariants(questionCreate.getVariants());
        question.setLanguage(question.getLanguage());
        return questionRepository.save(question);
    }

    @Override
    public QuizByIdResponse getQuizById(String id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));
        var result = quizMapper.toQuizByIdResponse(quiz);
        var questionsOfQuiz = quiz.getQuestionsWithDuration().stream().map(i -> i.getQuestion().getContent()).toList();


        result.setQuestions(questionsOfQuiz);
        result.setQuestionsCount(questionsOfQuiz.size());

        return result;
    }

    @Override
    public QuizByIdResponse getRandomQuiz() {
        SampleOperation sampleStage = Aggregation.sample(1);
        Aggregation aggregation = Aggregation.newAggregation(sampleStage);

        AggregationResults<Quiz> result = mongoTemplate.aggregate(aggregation, "quizzes", Quiz.class);
        Quiz quiz = result.getUniqueMappedResult();

        if (quiz == null) {
            throw new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "No quizzes found");
        }

        return quizMapper.toQuizByIdResponse(quiz);
    }


}

