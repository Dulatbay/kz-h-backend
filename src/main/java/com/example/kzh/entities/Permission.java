package com.example.kzh.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Permission {

    QUIZ_READ("quiz:read"),
    QUIZ_UPDATE("quiz:update"),
    QUIZ_DELETE("quiz:update"),
    QUIZ_CREATE("quiz:create");

    private final String permission;

    public static final Set<Permission> QUIZ_PERMISSIONS = Set.of(
            QUIZ_UPDATE,
            QUIZ_DELETE,
            QUIZ_CREATE,
            QUIZ_READ
    );
}
