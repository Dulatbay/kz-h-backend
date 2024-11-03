package com.example.kzh.config.validators;


import com.example.kzh.dto.request.QuizCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EitherOrValidator implements ConstraintValidator<EitherOr, QuizCreateRequest.QuestionCreateRequest> {

    @Override
    public void initialize(EitherOr constraintAnnotation) {
    }

    @Override
    public boolean isValid(QuizCreateRequest.QuestionCreateRequest value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean hasQuestionGenerate = value.getQuestionGenerate() != null;
        boolean hasQuestionCreate = value.getQuestionCreate() != null;

        return hasQuestionGenerate ^ hasQuestionCreate;
    }
}
