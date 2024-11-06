package com.example.kzh.mappers;


import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.Quiz;
import com.example.kzh.entities.QuizQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizResponse toQuizResponse(Quiz quiz);

    @Mapping(target = "questions", source = "quizQuestions", qualifiedByName = "mapQuestions")
    @Mapping(target = "questionsCount", expression = "java(quiz.getQuizQuestions().size())")
    QuizByIdResponse toQuizByIdResponse(Quiz quiz);

    @Named("mapQuestions")
    default List<String> mapQuestions(List<QuizQuestion> questionsWithDuration) {
        return questionsWithDuration.stream()
                .map(q -> q.getQuestion().getContent())
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "quizQuestions", ignore = true)
    @Mapping(target = "verified", constant = "false")
    Quiz toQuizEntity(QuizCreateRequest quizCreateRequest);
}
