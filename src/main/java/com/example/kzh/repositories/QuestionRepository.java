package com.example.kzh.repositories;

import com.example.kzh.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
}
