package com.example.kzh.mappers;

import com.example.kzh.dto.response.SoloGameQuestion;
import com.example.kzh.dto.response.SoloGameResponse;
import com.example.kzh.entities.SoloGame;
import com.example.kzh.entities.helpers.Variant;
import com.example.kzh.mappers.dto.request.SoloGameCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SoloGameMapper {
    @Mapping(target = "gameId", source = "id")
    @Mapping(target = "totalQuestions", expression = "java(soloGame.getQuiz().getQuizQuestions().size())")
    @Mapping(target = "currentQuestionIndex", source = "currentQuestionIdx")
    @Mapping(target = "currentQuestion", source = "soloGame", qualifiedByName = "mapQuestion")
    @Mapping(target = "previousQuestion", ignore = true)
    SoloGameResponse toResponse(SoloGame soloGame);

    @Named("mapQuestion")
    default SoloGameQuestion mapQuestion(SoloGame soloGame) {
        var currentQuestion = soloGame.getQuiz().getQuizQuestions().get(soloGame.getCurrentQuestionIdx());


        // todo: create mapper
        SoloGameQuestion soloGameQuestion = new SoloGameQuestion();
        soloGameQuestion.setQuizQuestionId(currentQuestion.getId());
        soloGameQuestion.setQuestion(currentQuestion.getQuestion().getContent());
        soloGameQuestion.setQuestionIdx(soloGame.getCurrentQuestionIdx());
        soloGameQuestion.setDuration(currentQuestion.getDurationInSeconds());
        soloGameQuestion.setVariants(currentQuestion.getVariants().stream().map(Variant::getText).collect(Collectors.toList()));

        return soloGameQuestion;
    }

    @Mapping(target = "attempts", expression = "java(java.util.List.of(soloGameCreateRequest.firstAttempt()))")
    SoloGame toEntity(SoloGameCreateRequest soloGameCreateRequest);
}
