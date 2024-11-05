package com.example.kzh.mappers;

import com.example.kzh.dto.request.QuizCreateRequest;
import com.example.kzh.entities.Question;
import com.example.kzh.entities.Topic;
import com.example.kzh.repositories.TopicRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {

    protected TopicRepository topicRepository;

    @Mapping(target = "content", source = "question")
    @Mapping(target = "topics", source = "topicId", qualifiedByName = "mapTopics")
    public abstract Question toQuestion(QuizCreateRequest.QuestionCreate questionCreate);

    @Named("mapTopics")
    protected Set<Topic> mapTopics(Set<String> topicIds) {
        return topicRepository.findByIdIn(topicIds);
    }
}
