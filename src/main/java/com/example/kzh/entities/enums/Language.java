package com.example.kzh.entities.enums;

public enum Language {
    KAZ, RU;

    public static Language getLanguage(String language) {
        return switch (language.toUpperCase()) {
            case "KAZ" -> KAZ;
            case "RU" -> RU;
            default -> RU;
        };
    }

}
