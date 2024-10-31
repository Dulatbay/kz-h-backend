package com.example.kzh.repositories;

import com.example.kzh.entities.QuizQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionsRepository extends JpaRepository<QuizQuestions, Long> {
    List<QuizQuestions> findQuizQuestionsByQuizId(Long id);
}
