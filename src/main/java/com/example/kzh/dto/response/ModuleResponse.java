package com.example.kzh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse {
    private Long moduleId;
    private String moduleName;
    private List<TopicResponse> topics;

    @AllArgsConstructor
    @Data
    public static class TopicResponse {
        private Long topicId;
        private String topicName;
    }
}