package com.example.kzh.services.impl;

import com.example.kzh.dto.response.ModuleResponse;
import com.example.kzh.entities.Topic;
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


    @Override
    public List<ModuleResponse> getModulesAndTheirTopics() {
        return null;
    }

}
