package com.example.kzh.mappers;


import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.AdminQuizDetailResponse;
import com.example.kzh.dto.response.AdminQuizResponse;
import com.example.kzh.dto.response.QuizByIdResponse;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.Quiz;
import com.example.kzh.entities.QuizQuestion;
import com.example.kzh.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    @Mapping(target = "questions", expression = "java(quiz.getQuizQuestions().size())")
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

    @Mapping(target = "author", expression = "java(quiz.getAuthor().getUsername())")
    AdminQuizResponse toAdminQuizResponse(Quiz quiz);


    @Mapping(target = "quizId", source = "id")
    @Mapping(target = "quizName", source = "title")
    @Mapping(target = "quizDescription", source = "description")
    @Mapping(target = "verifiedBy", expression = "java(quiz.getVerifiedBy().getUsername())")
    @Mapping(target = "questions", source = "quiz", qualifiedByName = "mapAdminQuestions")
    AdminQuizDetailResponse toAdminQuizDetailResponse(Quiz quiz);

    @Named("mapAdminQuestions")
    default List<AdminQuizDetailResponse.Question> mapAdminQuestions(Quiz quiz) {
        List<AdminQuizDetailResponse.Question> result = new ArrayList<>();

        quiz.getQuizQuestions()
                .forEach(quizQuestion -> {
                    var adminQuestion = new AdminQuizDetailResponse.Question();
                    adminQuestion.setId(quizQuestion.getQuestion().getId());
                    adminQuestion.setVerified(quizQuestion.isVerified());
                    adminQuestion.setContent(quizQuestion.getQuestion().getContent());
                    adminQuestion.setBaseVerified(quizQuestion.getQuestion().isVerified());
                    adminQuestion.setVariants(quizQuestion.getVariants().stream().toList());
                    result.add(adminQuestion);
                });

        return result;
    }

}
