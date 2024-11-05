package com.example.kzh.entities;

import com.example.kzh.entities.base.BaseEntity;
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
public class Topic extends BaseEntity {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private String titleKaz;

    @Indexed(unique = true)
    private String titleRu;

    @Indexed
    private int number;

    @DBRef
    private KzhModule kzhModule;

    @DBRef
    private User user;
}