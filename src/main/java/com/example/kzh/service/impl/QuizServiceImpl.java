package com.example.kzh.service.impl;

import com.example.kzh.dto.request.QuizRequest;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.exception.DbNotFoundException;
import com.example.kzh.mappers.QuizMapper;
import com.example.kzh.repositories.QuizRepository;
import com.example.kzh.repositories.UserRepository;
import com.example.kzh.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final UserRepository userRepository;


    @Override
    public Page<QuizResponse> getQuizzes(QuizRequest request, Principal principal) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        Long userId = null;

        if (principal != null) {
            userId = userRepository.findUserByEmail(principal.getName())
                    .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "User by email did not exist")).getId();
        }

        return quizRepository.findAllByFilters(userId, request.getSearchText(), request.getStatus(), request.getDifficulty(), request.getTopics(), pageRequest);
    }
}

