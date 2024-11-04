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
import java.util.Locale;


@RestController
@RequiredArgsConstructor
@RequestMapping("/modules")
@Slf4j
public class ModuleController {

    private final ModuleService moduleService;
    private final DataInitializationService dataInitializationService;

    @GetMapping
    public ResponseEntity<List<ModuleResponse>> getAllTopicWithModules() {
        var locale = LocaleContextHolder.getLocale();
        log.info("locale.getDisplayLanguage: {}", locale.getDisplayLanguage());
        log.info("locale.getLanguage: {}", locale.getLanguage());
        log.info("locale.getCountry: {}", locale.getCountry());
        log.info("locale.getISO3Country: {}", locale.getISO3Country());
        log.info("locale.getISO3Language: {}", locale.getISO3Language());
        return ResponseEntity.ok(moduleService.getModulesAndTheirTopics());
    }

    @PostMapping("/initialize")
    @PreAuthorize("hasAuthority('module:create')")
    public ResponseEntity<Void> initializeModules() {
        dataInitializationService.initializeModules();
        return ResponseEntity.status(201).build();
    }

}
