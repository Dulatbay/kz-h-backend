package com.example.kzh.mappers;

import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.dto.response.QuestionResponse;
import com.example.kzh.entities.Question;
import com.example.kzh.entities.Topic;
import com.example.kzh.entities.enums.Language;
import com.example.kzh.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@RequiredArgsConstructor
@Component
public abstract class QuestionMapper {

    @Autowired
    protected TopicRepository topicRepository;

    @Mapping(target = "content", source = "question")
    @Mapping(target = "topics", source = "topicId", qualifiedByName = "mapEntityTopics")
    public abstract Question toQuestion(QuizCreateRequest.QuestionCreate questionCreate);

    @Named("mapEntityTopics")
    protected Set<Topic> mapEntityTopics(Set<String> topicIds) {
        return topicRepository.findByIdIn(topicIds);
    }

    @Mapping(target = "questionId", source = "id")
    @Mapping(target = "question", source = "content")
    @Mapping(target = "topicIds", source = "topics", qualifiedByName = "mapTopics")
    public abstract QuestionResponse toQuestionResponse(Question question);

    @Named("mapTopics")
    protected List<QuestionResponse.Topic> mapTopics(Set<Topic> topics) {
        var locale = LocaleContextHolder.getLocale();
        var language = Language.getLanguage(locale.getLanguage());

        return topics.stream().map(topic -> {
            var questionTopics = new QuestionResponse.Topic();
            questionTopics.setTopicId(topic.getId());
            questionTopics.setTopicName(switch (language) {
                case RU -> topic.getTitleRu();
                case KAZ -> topic.getTitleKaz();
            });

            return questionTopics;
        }).toList();
    }

}
