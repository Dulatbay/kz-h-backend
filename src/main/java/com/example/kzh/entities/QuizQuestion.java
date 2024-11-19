package com.example.kzh.entities;

import com.example.kzh.entities.helpers.Variant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@Document(collection = "quiz_question")
@Accessors(chain = true)
public class QuizQuestion {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @DBRef
    private Question question;


    private Set<Variant> variants;
    private int durationInSeconds;

    public QuizQuestion(Question question, Set<Variant> variants, int durationInSeconds) {
        this.question = question;
        this.variants = variants;
        this.durationInSeconds = durationInSeconds;
    }
}
