package com.example.kzh.mappers;

import com.example.kzh.dto.response.SoloGamePreviousQuestion;
import com.example.kzh.dto.response.SoloGameResponse;
import com.example.kzh.entities.QuizQuestion;
import com.example.kzh.entities.SoloGame;
import com.example.kzh.entities.helpers.SoloGameAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface SoloGameAttemptMapper {
    @Mapping(target = "quizQuestion", source = "quizQuestion")
    @Mapping(target = "selectedVariants", ignore = true)
    @Mapping(target = "startTime", source = "quizQuestion", qualifiedByName = "currentTime")
    @Mapping(target = "endTime", ignore = true)
    SoloGameAttempt create(QuizQuestion quizQuestion);

    @Named("currentTime")
    default LocalDateTime currentTime(QuizQuestion quizQuestion) {
        return LocalDateTime.now();
    }

    @Mapping(target = "quizQuestionId", expression = "java(previousAttempt.quizQuestion().getId())")
    @Mapping(target = "question", expression = "java(previousAttempt.quizQuestion().getQuestion().getContent())")
    @Mapping(target = "questionIdx", expression = "java(game.getCurrentQuestionIdx())")
    @Mapping(target = "variants", expression = "java(previousAttempt.selectedVariants())")
    SoloGamePreviousQuestion toResponse(SoloGame game, SoloGameAttempt previousAttempt);
}
