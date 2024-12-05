package com.example.kzh.services;

import com.example.kzh.dto.params.PaginationParams;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.dto.response.QuestionResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

public interface QuestionService {
    PaginatedResponse<QuestionResponse> getQuestions(@Valid PaginationParams paginationParams);
}
