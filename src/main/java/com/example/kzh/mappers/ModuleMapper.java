package com.example.kzh.mappers;

import com.example.kzh.dto.response.ModuleResponse;
import com.example.kzh.entities.KzhModule;
import com.example.kzh.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    @Mapping(source = "id", target = "moduleId")
    @Mapping(source = "titleRu", target = "moduleName")
    ModuleResponse toModuleResponse(KzhModule module);

    @Mapping(source = "id", target = "topicId")
    @Mapping(source = "title", target = "topicName")
    ModuleResponse.TopicResponse toTopicResponse(Topic topic);

    List<ModuleResponse> toModuleResponseList(List<KzhModule> modules);
}

