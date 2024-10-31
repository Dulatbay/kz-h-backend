package com.example.kzh.entities.enums;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum Language {
    KAZ, RU, ENG;

    public Language getFromLocale(Locale locale) {
        return switch (locale.getLanguage()) {
            case "ru" -> RU;
            case "en" -> ENG;
            default -> KAZ;
        };
    }
}
