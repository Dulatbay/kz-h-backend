package com.example.kzh.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Level {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    int level;

    Level(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return String.valueOf(this.level);
    }
}
