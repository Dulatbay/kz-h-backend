package com.example.kzh.service.impl;

import com.example.kzh.dto.response.ModulesResponse;
import com.example.kzh.repositories.ModuleRepository;
import com.example.kzh.service.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public List<ModulesResponse> getModulesAndTheirTopics() {
        return moduleRepository.findAllModulesAndTheirTopics();
    }
}
