package com.example.kzh.entities;

import com.example.kzh.entities.enums.Language;
import com.example.kzh.entities.enums.Level;
import com.example.kzh.entities.helpers.Variant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Set;

@Document(collection = "questions")
@Accessors(chain = true)
@Getter
@Setter
public class Question {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(targetType = FieldType.STRING)
    private Language language;

    private String content;
    private String imageUrl;
    private Level level;

    @DBRef
    private User user;

    @DBRef
    private Set<Topic> topics;

    private Set<Variant> variants;
}
