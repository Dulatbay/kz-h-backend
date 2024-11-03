package com.example.kzh.entities.helpers;

import com.example.kzh.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class QuizQuestion {
    @DBRef
    private Question question;

    private Set<Variant> variants;
    private int durationInSeconds;
}
