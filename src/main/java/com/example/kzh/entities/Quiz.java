package com.example.kzh.entities;

import com.example.kzh.entities.base.BaseEntity;
import com.example.kzh.entities.enums.Language;
import com.example.kzh.entities.enums.Level;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "quizzes")
@Accessors(chain = true)
@Getter
@Setter
public class Quiz extends BaseEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String title;
    private String description;
    private boolean isVerified;

    @Field(targetType = FieldType.STRING)
    private Level level;

    @Field(targetType = FieldType.STRING)
    private Language language;
    private boolean showQuestions;

    @DBRef
    private User verifiedBy;

    @DBRef
    private User author;

    private List<QuizQuestion> quizQuestions = new ArrayList<>();
}
