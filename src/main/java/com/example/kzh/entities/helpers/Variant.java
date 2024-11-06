package com.example.kzh.entities.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class Variant {
    private String text;
    private boolean isCorrect;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Variant variant = (Variant) obj;
        return isCorrect == variant.isCorrect && text.equals(variant.text);
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }
}
