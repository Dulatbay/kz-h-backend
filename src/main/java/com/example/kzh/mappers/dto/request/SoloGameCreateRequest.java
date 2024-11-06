package com.example.kzh.mappers.dto.request;

import com.example.kzh.entities.Quiz;
import com.example.kzh.entities.User;
import com.example.kzh.entities.helpers.SoloGameAttempt;


public record SoloGameCreateRequest(User player, Quiz quiz, SoloGameAttempt firstAttempt) { }
