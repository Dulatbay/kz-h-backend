package com.example.kzh.repositories;

import com.example.kzh.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = """
            SELECT q.*
            FROM quiz qz
            LEFT JOIN quiz_question qzqn ON qzqn.quiz_id = qz.id
            LEFT JOIN question q ON qzqn.question_id = q.id
            WHERE qz.id = :id
            """, nativeQuery = true)
    List<Question> findAllQuestionsByQuizId(@Param("id") Long id);
}
