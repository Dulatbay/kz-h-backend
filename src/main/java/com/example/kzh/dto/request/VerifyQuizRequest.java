package com.example.kzh.dto.request;


import java.util.List;

public record VerifyQuizRequest(List<String> approveQuestionIds) {}
