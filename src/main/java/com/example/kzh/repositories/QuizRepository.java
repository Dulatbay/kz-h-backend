package com.example.kzh.repositories;

import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query(value = """
            SELECT q.id,
                    count(passed_q) > 0 AS status,
                   q.title,
                   CEIL(AVG(passed_q.average)) AS average,
                   CEIL(AVG(q.level)) AS difficulty,
                   count(qq) AS questions
            FROM quiz AS q
                     LEFT JOIN quiz_questions AS qq ON qq.quiz_id = q.id
                     LEFT JOIN public.passed_quiz AS passed_q
                               ON q.id = passed_q.quiz_id AND (:userId IS NULL OR passed_q.user_id = :userId)
                     LEFT JOIN question AS ques ON ques.id = qq.question_id AND (:topics IS NULL OR ques.id IN :topics)
            WHERE (:searchText IS NULL OR (:searchText LIKE '%' || q.title || '%' OR :searchText LIKE '%' || q.description || '%'))
            GROUP BY q.title, q.id
            HAVING (:status IS NULL OR (:status = true AND count(passed_q) > 0))
               AND (:difficulty IS NULL OR (:difficulty = CEIL(AVG(passed_q.average))))
            """, nativeQuery = true)
    Page<QuizResponse> findAllByFilters(Long userId, String searchText, Boolean status, Integer difficulty, List<Long> topics, Pageable pageable);
}
