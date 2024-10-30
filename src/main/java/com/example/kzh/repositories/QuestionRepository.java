package com.example.kzh.repositories;

import com.example.kzh.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = """
                    SELECT q.*
                    FROM Question q
                    JOIN QuizQuestions qq ON q.id = qq.question.id
                    WHERE qq.quiz.id = :id
            """, nativeQuery = true)
    List<Question> findByQuizId(Long id);
}
