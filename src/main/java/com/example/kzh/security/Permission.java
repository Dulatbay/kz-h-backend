package com.example.kzh.security;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public enum Permission {

    QUIZ_PLAY("quiz:play"),
    QUIZ_READ("quiz:read"),
    QUIZ_UPDATE("quiz:update"),
    QUIZ_DELETE("quiz:delete"),
    QUIZ_CREATE("quiz:create"),
    QUIZ_VERIFY("quiz:verify"),
    MODULE_CREATE("module:create");

    public final String permission;

    public static final Set<Permission> QUIZ_PERMISSIONS = Set.of(
            QUIZ_UPDATE,
            QUIZ_DELETE,
            QUIZ_CREATE,
            QUIZ_PLAY,
            QUIZ_READ
    );
}
