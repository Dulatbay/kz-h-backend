package com.example.kzh.services.impl;

import com.example.kzh.dto.response.SoloGameProcess;
import com.example.kzh.dto.response.SoloGameResultResponse;
import com.example.kzh.entities.SoloGame;
import com.example.kzh.entities.User;
import com.example.kzh.entities.helpers.SoloGameAttempt;
import com.example.kzh.entities.helpers.Variant;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.mappers.SoloGameAttemptMapper;
import com.example.kzh.mappers.SoloGameMapper;
import com.example.kzh.mappers.dto.request.SoloGameCreateRequest;
import com.example.kzh.repositories.QuizRepository;
import com.example.kzh.repositories.SoloGameRepository;
import com.example.kzh.services.SoloGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SoloGameServiceImpl implements SoloGameService {
    private final SoloGameMapper soloGameMapper;
    private final SoloGameAttemptMapper soloGameAttemptMapper;
    private final SoloGameRepository soloGameRepository;
    private final QuizRepository quizRepository;

    @Override
    public SoloGameProcess startSoloQuiz(String quizId, User currentUser) {
        var soloGame = createGameSession(quizId, currentUser);

        return soloGameMapper.toProcessResponse(soloGame);
    }

    private SoloGame createGameSession(String quizId, User player) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Quiz not found"));

        var firstQuizQuestion = quiz.getQuizQuestions().getFirst();
        var firstAttempt = soloGameAttemptMapper.create(firstQuizQuestion);

        var gameToCreate = soloGameMapper.toEntity(new SoloGameCreateRequest(player, quiz, firstAttempt));

        return soloGameRepository.save(gameToCreate);
    }

    // todo: refactoring
    @Override
    public SoloGameProcess playSoloQuiz(String gameId, User currentUser, List<String> selectedVariants) {
        var game = soloGameRepository.findById(gameId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Game not found"));

        SoloGameAttempt previousAttempt = checkAnswerAndSaveAttempt(game, selectedVariants);

        if (game.getCurrentQuestionIdx() + 1 == game.getQuiz().getQuizQuestions().size()) {
            game.setFinished(true);
            soloGameRepository.save(game);

            var soloGameResponse = soloGameMapper.toProcessResponse(game);
            soloGameResponse.setCurrentQuestion(null);
            soloGameResponse.setPreviousQuestion(soloGameAttemptMapper.toResponse(game, previousAttempt));

            return soloGameResponse;
        }

        iterateToNextQuestion(game);

        var soloGameResponse = soloGameMapper.toProcessResponse(game);
        soloGameResponse.setPreviousQuestion(soloGameAttemptMapper.toResponse(game, previousAttempt));

        return soloGameResponse;
    }

    // todo: грязная функция
    private void iterateToNextQuestion(SoloGame game) {
        var quiz = game.getQuiz();
        var nextQuizQuestionIdx = game.getCurrentQuestionIdx() + 1;

        var quizQuestion = quiz.getQuizQuestions().get(nextQuizQuestionIdx);
        var attempt = soloGameAttemptMapper.create(quizQuestion);

        game.setCurrentQuestionIdx(nextQuizQuestionIdx);
        game.getAttempts().add(attempt);
        game = soloGameRepository.save(game);
    }

    // todo: грязная функция
    private SoloGameAttempt checkAnswerAndSaveAttempt(SoloGame game, List<String> selectedOptions) {
        var answeredQuizQuestionIdx = game.getCurrentQuestionIdx();
        var answeredQuizQuestion = game.getQuiz().getQuizQuestions().get(answeredQuizQuestionIdx);
        var targetAttempt = game.getAttempts().get(answeredQuizQuestionIdx);
        var variants = answeredQuizQuestion.getVariants();


        if (answeredQuizQuestion.getDurationInSeconds() != -1) {
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

    @Override
    public SoloGameResultResponse findGame(String gameId) {
        var soloGame = soloGameRepository.findById(gameId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Game not found"));


        // todo: write to mappers
        SoloGameResultResponse response = new SoloGameResultResponse();
        response.setGameId(soloGame.getId());

        response.setQuizId(soloGame.getQuiz().getId());
        response.setQuizName(soloGame.getQuiz().getTitle());

        long durationInSeconds = soloGame.getAttempts()
                .stream()
                .reduce(0L,
                        (x, y) -> x + y.getDuration().getSeconds(),
                        Long::sum);
        response.setDuration(durationInSeconds);

        var questionCount = soloGame.getQuiz().getQuizQuestions().size();
        var correctAnswers = soloGame.getAttempts()
                .stream()
                .filter(SoloGameAttempt::isFullyCorrect)
                .count();

        // todo: проверить деление на 0
        response.setResult((double) correctAnswers / questionCount * 100);
        response.setRecord(0);
        response.setBeats(0);

        return response;
    }
}
