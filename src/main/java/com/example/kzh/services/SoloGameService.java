package com.example.kzh.services;

import com.example.kzh.dto.response.SoloGameProcess;
import com.example.kzh.dto.response.SoloGameResultResponse;
import com.example.kzh.entities.User;

import java.util.List;

public interface SoloGameService {
    SoloGameProcess startSoloQuiz(String quizId, User currentUser);
    SoloGameProcess playSoloQuiz(String gameId, User currentUser, List<String> selectedVariants);
    SoloGameResultResponse findGame(String gameId);
}
