package com.example.kzh.services;

import com.example.kzh.dto.response.ModuleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ModuleService {
    List<ModuleResponse> getModulesAndTheirTopics();
}
