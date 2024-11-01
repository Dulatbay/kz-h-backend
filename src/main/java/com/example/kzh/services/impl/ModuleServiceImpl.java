package com.example.kzh.services.impl;

import com.example.kzh.dto.response.ModuleResponse;
import com.example.kzh.entities.Topic;
import com.example.kzh.repositories.ModuleRepository;
import com.example.kzh.repositories.TopicRepository;
import com.example.kzh.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final TopicRepository topicRepository;

    @Override
    public List<ModuleResponse> getModulesAndTheirTopics() {
        List<ModuleResponse> result = new ArrayList<>();

        var modules = moduleRepository.findAll(Sort.by("id"));
        var topics = topicRepository.findAll(Sort.by("module_id"));

        Map<Long, List<Topic>> topicsByModuleId = topics.stream()
                .collect(Collectors.groupingBy(topic -> topic.getModule().getId()));

        modules.forEach(module -> {
            ModuleResponse mr = new ModuleResponse();
            mr.setModuleId(module.getId());
            mr.setModuleName(module.getTitle());

            List<ModuleResponse.TopicResponse> topicResponses = topicsByModuleId
                    .getOrDefault(module.getId(), Collections.emptyList()).stream()
                    .map(topic -> new ModuleResponse.TopicResponse(topic.getId(), topic.getTitle()))
                    .toList();

            mr.setTopics(topicResponses);
            result.add(mr);
        });

        return result;
    }

}
