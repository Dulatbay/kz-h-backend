package com.example.kzh.controllers;

import com.example.kzh.dto.response.ModuleResponse;
import com.example.kzh.services.DataInitializationService;
import com.example.kzh.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/modules")
@Slf4j
public class ModuleController {

    private final ModuleService moduleService;
    private final DataInitializationService dataInitializationService;

    @GetMapping
    public ResponseEntity<List<ModuleResponse>> getAllTopicWithModules() {
        return ResponseEntity.ok(moduleService.getModulesAndTheirTopics());
    }

    @PostMapping("/initialize")
    @PreAuthorize("hasAuthority('module:create')")
    public ResponseEntity<Void> initializeModules() {
        dataInitializationService.initializeModules();
        return ResponseEntity.status(201).build();
    }

}
