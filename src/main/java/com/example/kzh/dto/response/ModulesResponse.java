package com.example.kzh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModulesResponse {
    private Long moduleId;
    private String moduleName;
    private List<Long> topicId;
    private List<String> topicName;
}
