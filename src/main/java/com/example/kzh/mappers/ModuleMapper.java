package com.example.kzh.mappers;

import com.example.kzh.dto.response.ModuleResponse;
import com.example.kzh.entities.KzhModule;
import com.example.kzh.entities.Topic;
import com.example.kzh.entities.enums.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    @Mapping(source = "id", target = "moduleId")
    @Mapping(source = "module", target = "moduleName", qualifiedByName = "mapModuleTitle")
    ModuleResponse toModuleResponse(KzhModule module);

    @Named("mapModuleTitle")
    default String mapModuleTitle(KzhModule module) {
        var locale = LocaleContextHolder.getLocale();
        var language = Language.getLanguage(locale.getLanguage());

        return switch (language) {
            case RU -> module.getTitleRu();
            case KAZ -> module.getTitleKaz();
        };
    }

    @Mapping(source = "id", target = "topicId")
    @Mapping(source = "topic", target = "topicName", qualifiedByName = "mapTopicTitle")
    ModuleResponse.TopicResponse toTopicResponse(Topic topic);

    @Named("mapTopicTitle")
    default String mapTopicTitle(Topic topic) {
        var locale = LocaleContextHolder.getLocale();
        var language = Language.getLanguage(locale.getLanguage());

        return switch (language) {
            case RU -> topic.getTitleRu();
            case KAZ -> topic.getTitleKaz();
        };
    }

    List<ModuleResponse> toModuleResponseList(List<KzhModule> modules);
}

