package com.example.kzh.mappers;

import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.entities.Question;
import com.example.kzh.entities.User;
import com.example.kzh.entities.enums.Language;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {
    private final TopicRepository topicRepository;

    public Question mapFromCreateRequest(QuizCreateRequest.QuestionCreateRequest questionToCreate, User user) {
        var question = new Question();
        question.setQuestionText(questionToCreate.question);
        question.setUser(user);
        question.setHasImage(false);
        question.setLevel(questionToCreate.level);
        question.setVerified(false);
        question.setHasTerm(false);
        question.setTopic(topicRepository.findById(questionToCreate.topicId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Topic not found")));

        return question;
    }
}
