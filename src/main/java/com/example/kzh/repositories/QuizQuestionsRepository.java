package com.example.kzh.repositories;

import com.example.kzh.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionsRepository extends JpaRepository<QuizQuestion, Long> {
}
