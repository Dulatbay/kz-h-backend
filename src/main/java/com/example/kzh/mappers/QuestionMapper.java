package com.example.kzh.mappers;

import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.entities.Question;
import com.example.kzh.entities.Topic;
import com.example.kzh.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@RequiredArgsConstructor
@Component
public abstract class QuestionMapper {

    @Autowired
    protected TopicRepository topicRepository;

    @Mapping(target = "content", source = "question")
    @Mapping(target = "topics", source = "topicId", qualifiedByName = "mapTopics")
    public abstract Question toQuestion(QuizCreateRequest.QuestionCreate questionCreate);

    @Named("mapTopics")
    protected Set<Topic> mapTopics(Set<String> topicIds) {
        return topicRepository.findByIdIn(topicIds);
    }
}
