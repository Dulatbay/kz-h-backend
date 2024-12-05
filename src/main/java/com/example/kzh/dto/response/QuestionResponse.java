package com.example.kzh.dto.response;

import com.example.kzh.entities.helpers.Variant;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class QuestionResponse {
    private String questionId;
    private String question;
    private String topicName;
    private List<Topic> topicIds;
    private Set<Variant> variants;

    @Data
    public static class Topic {
        private String topicId;
        private String topicName;
    }
}
