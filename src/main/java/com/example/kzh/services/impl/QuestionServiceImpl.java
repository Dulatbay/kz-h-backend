package com.example.kzh.services.impl;

import com.example.kzh.dto.params.PaginationParams;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.dto.response.QuestionResponse;
import com.example.kzh.entities.Question;
import com.example.kzh.mappers.QuestionMapper;
import com.example.kzh.repositories.QuestionRepository;
import com.example.kzh.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public PaginatedResponse<QuestionResponse> getQuestions(PaginationParams paginationParams) {
        PageRequest pageable = PageRequest.of(paginationParams.getPage(), paginationParams.getSize());
        Page<Question> questions = questionRepository.findAllByIsVerifiedIsTrueOrderByIdDesc(pageable);

        var result = questions.map(questionMapper::toQuestionResponse);
        return new PaginatedResponse<>(result);
    }
}
