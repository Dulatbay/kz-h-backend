package com.example.kzh.services.impl;

import com.example.kzh.dto.params.QuizParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.*;
import com.example.kzh.entities.*;
import com.example.kzh.entities.helpers.SoloGameAttempt;
import com.example.kzh.entities.helpers.Variant;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.mappers.QuestionMapper;
import com.example.kzh.mappers.QuizMapper;
import com.example.kzh.mappers.SoloGameAttemptMapper;
import com.example.kzh.mappers.SoloGameMapper;
import com.example.kzh.mappers.dto.request.SoloGameCreateRequest;
import com.example.kzh.repositories.*;
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

import java.time.Duration;
import java.time.LocalDateTime;
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
    private final QuestionMapper questionMapper;
    private final SoloGameMapper soloGameMapper;
    private final SoloGameAttemptMapper soloGameAttemptMapper;
    private final SoloGameRepository soloGameRepository;
    private final QuizQuestionRepository quizQuestionRepository;

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
                var question = questionMapper.toQuestion(questionCreateReq);

                quizQuestion = createQuizQuestion(questionRepository.save(question),
                        questionCreateReq.getVariants(),
                        questionCreateReq.getDurationInSeconds());
            }

            quizQuestion = quizQuestionRepository.save(quizQuestion);

            quiz.getQuizQuestions().add(quizQuestion);
        });

        quizRepository.save(quiz);
        log.info("Quiz created with ID: {}", quiz.getId());
    }

    private QuizQuestion createQuizQuestion(Question question, List<Variant> variants, int duration) {
        return new QuizQuestion(question, variants, duration);
    }


    private Question generateQuestion(@Valid QuizCreateRequest.QuestionGenerate questionGenerate) {
        var questionId = questionGenerate.getQuestionId();

        return questionRepository.findById(questionId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Question not found"));
    }


    @Override
    public QuizByIdResponse getQuizById(String id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));
        var result = quizMapper.toQuizByIdResponse(quiz);
        var questionsOfQuiz = quiz.getQuizQuestions().stream().map(i -> i.getQuestion().getContent()).toList();


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

    @Override
    @Transactional
    public SoloGameResponse startSoloQuiz(String quizId, User currentUser) {
        var soloGame = createGameSession(quizId, currentUser);

        return soloGameMapper.toResponse(soloGame);
    }

    private SoloGame createGameSession(String quizId, User player) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));

        var firstQuizQuestion = quiz.getQuizQuestions().getFirst();
        var firstAttempt = soloGameAttemptMapper.create(firstQuizQuestion);

        var gameToCreate = soloGameMapper.toEntity(new SoloGameCreateRequest(player, quiz, firstAttempt));

        return soloGameRepository.save(gameToCreate);
    }

    @Override
    @Transactional
    public SoloGameResponse playSoloQuiz(String gameId, User currentUser, List<String> selectedOptions) {
        var game = soloGameRepository.findById(gameId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Game not found"));

        SoloGameAttempt previousAttempt = checkAnswerAndSaveAttempt(game, selectedOptions);

        if (game.getCurrentQuestionIdx() + 1 == game.getQuiz().getQuizQuestions().size()) {
            var soloGameResponse = soloGameMapper.toResponse(game);
            soloGameResponse.setCurrentQuestion(null);
            soloGameResponse.setPreviousQuestion(soloGameAttemptMapper.toResponse(game, previousAttempt));

            return soloGameResponse;
        }

        iterateToNextQuestion(game);

        var soloGameResponse = soloGameMapper.toResponse(game);
        soloGameResponse.setPreviousQuestion(soloGameAttemptMapper.toResponse(game, previousAttempt));

        return soloGameResponse;
    }

    private void iterateToNextQuestion(SoloGame game) {
        var quiz = game.getQuiz();
        var nextQuizQuestionIdx = game.getCurrentQuestionIdx() + 1;

        var quizQuestion = quiz.getQuizQuestions().get(nextQuizQuestionIdx);
        var attempt = soloGameAttemptMapper.create(quizQuestion);

        game.setCurrentQuestionIdx(nextQuizQuestionIdx);
        game.getAttempts().add(attempt);
        game = soloGameRepository.save(game);
    }

    private SoloGameAttempt checkAnswerAndSaveAttempt(SoloGame game, List<String> selectedOptions) {
        var answeredQuizQuestionIdx = game.getCurrentQuestionIdx();
        var answeredQuizQuestion = game.getQuiz().getQuizQuestions().get(answeredQuizQuestionIdx);
        var targetAttempt = game.getAttempts().get(answeredQuizQuestionIdx);
        var variants = answeredQuizQuestion.getVariants();


        if(answeredQuizQuestion.getDurationInSeconds() != -1) {
            LocalDateTime questionEndTime = targetAttempt.startTime().plusSeconds(answeredQuizQuestion.getDurationInSeconds());

            if (LocalDateTime.now().isAfter(questionEndTime)) {
                targetAttempt.endTime(LocalDateTime.now());
                soloGameRepository.save(game);

                return targetAttempt;
            }
        }

        Set<String> correctAnswers = variants.stream()
                .filter(Variant::isCorrect)
                .map(Variant::getText)
                .collect(Collectors.toSet());


        targetAttempt.endTime(LocalDateTime.now());
        targetAttempt.selectedVariants(selectedOptions.stream().map(s -> new Variant(s, correctAnswers.contains(s))).collect(Collectors.toList()));


        soloGameRepository.save(game);

        return targetAttempt;
    }


}

