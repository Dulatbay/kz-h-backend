package com.example.kzh.entities.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Variant {
    private String text;
    private boolean isCorrect;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variant) {
            return text.equals(((Variant) obj).text) && isCorrect == ((Variant) obj).isCorrect;
        }
        return false;
    }
}
