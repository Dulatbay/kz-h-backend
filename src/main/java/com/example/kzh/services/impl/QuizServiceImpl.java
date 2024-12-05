package com.example.kzh.services.impl;

import com.example.kzh.dto.params.AdminQuizSearchParams;
import com.example.kzh.dto.params.QuizSearchParams;
import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.request.VerifyQuizRequest;
import com.example.kzh.dto.response.*;
import com.example.kzh.entities.*;
import com.example.kzh.entities.enums.Language;
import com.example.kzh.entities.helpers.Variant;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.mappers.QuestionMapper;
import com.example.kzh.mappers.QuizMapper;
import com.example.kzh.repositories.*;
import com.example.kzh.services.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import java.util.*;
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
    private final QuestionMapper questionMapper;
    private final QuizQuestionRepository quizQuestionRepository;

    @Override
    public Page<QuizResponse> getQuizzes(QuizSearchParams request, User user) {
        var locale = LocaleContextHolder.getLocale();
        request.setLanguage(Language.getLanguage(locale.getLanguage()));

        var pageable = PageRequest.of(request.getPage(), request.getSize());
        var query = getQuizQueryByParams(request).with(pageable);

        List<Quiz> quizzes = mongoTemplate.find(query, Quiz.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), Quiz.class);

        List<QuizResponse> quizResponses = quizzes.stream().map(quizMapper::toQuizResponse).collect(Collectors.toList());

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

                quizQuestion = createQuizQuestion(question, new HashSet<>(questionGenerateReq.getVariants()), questionGenerateReq.getDurationInSeconds());
            } else {
                var question = questionMapper.toQuestion(questionCreateReq);

                quizQuestion = createQuizQuestion(questionRepository.save(question), new HashSet<>(questionCreateReq.getVariants()), questionCreateReq.getDurationInSeconds());
            }

            quizQuestion = quizQuestionRepository.save(quizQuestion);

            quiz.getQuizQuestions().add(quizQuestion);
        });

        quizRepository.save(quiz);
        log.info("Quiz created with ID: {}", quiz.getId());
    }

    private QuizQuestion createQuizQuestion(Question question, Set<Variant> variants, int duration) {
        return new QuizQuestion(question, variants, duration);
    }


    private Question generateQuestion(@Valid QuizCreateRequest.QuestionGenerate questionGenerate) {
        var questionId = questionGenerate.getQuestionId();

        return questionRepository.findById(questionId).orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Question not found"));
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
    public void verifyFull(User verifiedBy, String quizId) {
        final Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));
        final List<QuizQuestion> quizQuestions = new ArrayList<>();
        final List<Question> questions = new ArrayList<>();


        quiz.getQuizQuestions()
                .forEach(quizQuestion -> {
                    quizQuestion.setVerified(true);
                    quizQuestion.setVerifiedBy(verifiedBy);


                    quizQuestion.getQuestion().setVerified(true);
                    quizQuestion.getQuestion().setVerifiedBy(verifiedBy);

                    quizQuestions.add(quizQuestion);
                    questions.add(quizQuestion.getQuestion());
                });

        quizQuestionRepository.saveAll(quizQuestions);
        questionRepository.saveAll(questions);

        quiz.setVerified(true);
        quiz.setVerifiedBy(verifiedBy);
        quizRepository.save(quiz);
    }

    @Override
    public Page<AdminQuizResponse> getAdminQuizzes(AdminQuizSearchParams quizSearchParams) {
        var locale = LocaleContextHolder.getLocale();
        quizSearchParams.setLanguage(Language.getLanguage(locale.getLanguage()));

        var pageable = PageRequest.of(quizSearchParams.getPage(), quizSearchParams.getSize());
        Query query = getQuizQueryByParams(quizSearchParams).with(pageable);

        List<Quiz> quizzes = mongoTemplate.find(query, Quiz.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), Quiz.class);

        List<AdminQuizResponse> adminQuizResponses = quizzes.stream()
//                .map(quizMapper::toAdminQuizResponse)
                .map(q -> new AdminQuizResponse())
                .toList();

        return new PageImpl<>(adminQuizResponses, pageable, count);
    }

    @Override
    public void verifyPartial(User userDetails,
                              String quizId,
                              VerifyQuizRequest verifyQuizRequest) {
        final Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));

        approveQuestions(quiz, verifyQuizRequest.approveQuestionIds(), userDetails);
    }

    @Override
    public AdminQuizDetailResponse getAdminQuizById(String quizId) {
        final Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));

//        return quizMapper.toAdminQuizDetailResponse(quiz);
        return null;
    }

    private void approveQuestions(Quiz quiz, List<String> questions, User verifiedBy) {
        TreeSet<String> questionTreeSet = new TreeSet<>(questions);
        List<Question> questionsToSave = new ArrayList<>();
        List<QuizQuestion> quizQuestionsToSave = new ArrayList<>();


        quiz.getQuizQuestions().stream()
                .filter(i -> questionTreeSet.contains(i.getQuestion().getId()))
                .forEach(quizQuestion -> {
                    quizQuestion.setVerified(true);
                    quizQuestion.setVerifiedBy(verifiedBy);

                    quizQuestion.getQuestion().setVerified(true);
                    quizQuestion.getQuestion().setVerifiedBy(verifiedBy);

                    quizQuestionsToSave.add(quizQuestion);
                    questionsToSave.add(quizQuestion.getQuestion());
                });

        quizQuestionRepository.saveAll(quizQuestionsToSave);
        questionRepository.saveAll(questionsToSave);

        quizRepository.save(quiz);
    }

    private static <T extends QuizSearchParams> Query getQuizQueryByParams(T quizSearchParams) {
        Query query = new Query();

        query.addCriteria(Criteria.where("language").is(quizSearchParams.getLanguage()));

        if (quizSearchParams.getLevel() != null) {
            query.addCriteria(Criteria.where("level").is(quizSearchParams.getLevel()));
        }
        if (quizSearchParams.getTopics() != null && !quizSearchParams.getTopics().isEmpty()) {
            query.addCriteria(Criteria.where("topics").in(quizSearchParams.getTopics()));
        }
        if (quizSearchParams.getSearchText() != null && !quizSearchParams.getSearchText().isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(quizSearchParams.getSearchText(), "i"));
        }

        if (quizSearchParams instanceof AdminQuizSearchParams adminSearchParams) {
            if (adminSearchParams.getVerified() != null)
                query.addCriteria(Criteria.where("isVerified").is(adminSearchParams.getVerified()));
            if (adminSearchParams.getAuthorName() != null && !adminSearchParams.getAuthorName().isEmpty())
                query.addCriteria(Criteria.where("author").regex(adminSearchParams.getAuthorName(), "i"));
        }

        return query;
    }
}

