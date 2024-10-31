package com.example.kzh.controllers;

import com.example.kzh.dto.response.ModuleResponse;
import com.example.kzh.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/modules")
@Slf4j
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping()
    public ResponseEntity<List<ModuleResponse>> getAllTopicWithModules(){
        return ResponseEntity.ok(moduleService.getModulesAndTheirTopics());
    }
}
