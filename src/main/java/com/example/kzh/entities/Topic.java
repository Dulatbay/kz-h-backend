package com.example.kzh.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "topics")
@Accessors(chain = true)
@Getter
@Setter
public class Topic {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private String title;

    @Indexed
    private int number;

    @DBRef
    private KzhModule kzhModule;

    @DBRef
    private User user;
}