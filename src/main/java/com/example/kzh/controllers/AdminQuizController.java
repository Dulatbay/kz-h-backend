package com.example.kzh.controllers;

import com.example.kzh.constants.Utils;
import com.example.kzh.dto.params.AdminQuizSearchParams;
import com.example.kzh.dto.params.QuizSearchParams;
import com.example.kzh.dto.request.VerifyQuizRequest;
import com.example.kzh.dto.response.AdminQuizDetailResponse;
import com.example.kzh.dto.response.AdminQuizResponse;
import com.example.kzh.dto.response.PaginatedResponse;
import com.example.kzh.services.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/quizzes")
@RequiredArgsConstructor
public class AdminQuizController {
    private final QuizService quizService;

    @PostMapping("{id}/verify-full")
    @PreAuthorize("hasAuthority(T(com.example.kzh.security.Permission).QUIZ_VERIFY.permission)")
    public ResponseEntity<Void> verifyFull(@PathVariable("id") String quizId) {
        var userDetails = Utils.getCurrentUser();

        quizService.verifyFull(userDetails, quizId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.example.kzh.security.Permission).QUIZ_VERIFY.permission)")
    public ResponseEntity<PaginatedResponse<AdminQuizResponse>> getQuizzes(@ModelAttribute
                                                                           @Valid
                                                                           AdminQuizSearchParams quizSearchParams) {

        Page<AdminQuizResponse> quizzes = quizService.getAdminQuizzes(quizSearchParams);

        return ResponseEntity.ok(new PaginatedResponse<>(quizzes));
    }

    @PostMapping("{id}/verify-partial")
    @PreAuthorize("hasAuthority(T(com.example.kzh.security.Permission).QUIZ_VERIFY.permission)")
    public ResponseEntity<Void> verifyPartial(@PathVariable("id") String quizId, @RequestBody VerifyQuizRequest verifyQuizRequest) {
        var userDetails = Utils.getCurrentUser();

        quizService.verifyPartial(userDetails, quizId, verifyQuizRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority(T(com.example.kzh.security.Permission).QUIZ_VERIFY.permission)")
    public ResponseEntity<AdminQuizDetailResponse> getDetailById(@PathVariable("id") String quizId) {
        return ResponseEntity.ok(quizService.getAdminQuizById(quizId));
    }

}
