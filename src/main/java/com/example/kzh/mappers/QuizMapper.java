package com.example.kzh.mappers;


import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    QuizResponse toQuizResponse(Quiz quiz);

    QuizByIdResponse toQuizByIdResponse(Quiz quiz);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "questionsWithDuration", ignore = true)
    @Mapping(target = "verified", constant = "false")
    Quiz toQuizEntity(QuizCreateRequest quizCreateRequest);
}
