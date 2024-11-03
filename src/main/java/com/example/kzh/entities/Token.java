package com.example.kzh.entities;

import com.example.kzh.entities.enums.TokenType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDateTime;

@Document(collection = "tokens")
@Accessors(chain = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private String token;

    @Field(targetType = FieldType.STRING)
    private TokenType tokenType;

    private boolean revoked;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    @DBRef
    private User user;
}
