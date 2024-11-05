package com.example.kzh.entities;

import com.example.kzh.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Document(collection = "modules")
@Accessors(chain = true)
@Setter
@Getter
public class KzhModule extends BaseEntity {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String titleKaz;
    private String titleRu;
    private String descriptionKaz;
    private String descriptionRu;
    private String level;
    private int hours;
    private int number;
    private String imageUrl;

    @DBRef
    private User user;

    @DBRef
    private Set<Topic> topics;
}
