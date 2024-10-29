package com.example.kzh.service;

import com.example.kzh.dto.response.ModulesResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModuleService {
    List<ModulesResponse> getModulesAndTheirTopics();
}
