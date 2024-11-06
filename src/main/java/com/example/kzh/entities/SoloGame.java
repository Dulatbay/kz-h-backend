package com.example.kzh.entities;

import com.example.kzh.entities.base.BaseEntity;
import com.example.kzh.entities.helpers.SoloGameAttempt;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document(collection = "solo_game")
@Accessors(chain = true)
public class SoloGame extends BaseEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @DBRef
    @Indexed
    private Quiz quiz;

    @DBRef
    private User player;

    private List<SoloGameAttempt> attempts;

    @Indexed
    private int currentQuestionIdx = 0;

    private int correctAnswersCount = 0;
    private boolean isFinished = false;
}
