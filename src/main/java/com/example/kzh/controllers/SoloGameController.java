package com.example.kzh.controllers;

import com.example.kzh.constants.Utils;
import com.example.kzh.dto.response.SoloGameProcess;
import com.example.kzh.dto.response.SoloGameResultResponse;
import com.example.kzh.entities.helpers.Variant;
import com.example.kzh.services.SoloGameService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solo-game")
public class SoloGameController {
    private final SoloGameService soloGameService;


    @PostMapping("/start/{quiz-id}")
    @PreAuthorize("hasAuthority('quiz:play')")
    public ResponseEntity<SoloGameProcess> startQuiz(@PathVariable("quiz-id") String quizId) {
        var currentUser = Utils.getCurrentUser();

        SoloGameProcess result = soloGameService.startSoloQuiz(quizId, currentUser);
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/next-question/{game-id}")
    @PreAuthorize("hasAuthority('quiz:play')")
    public ResponseEntity<SoloGameProcess> playQuiz(@PathVariable("game-id")
                                                    String gameId,
                                                    @RequestBody
                                                    @UniqueElements
                                                    List<String> selectedVariants) {
        var currentUser = Utils.getCurrentUser();

        SoloGameProcess result = soloGameService.playSoloQuiz(gameId, currentUser, selectedVariants);

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/{game-id}")
    public ResponseEntity<SoloGameResultResponse> findGame(@PathVariable("game-id") String gameId) {
        SoloGameResultResponse soloGameResultResponse = soloGameService.findGame(gameId);

        return ResponseEntity.status(200).body(soloGameResultResponse);
    }


}
