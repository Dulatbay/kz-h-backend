package com.example.kzh.repositories;

import com.example.kzh.dto.params.QuizRandomParams;
import com.example.kzh.dto.response.QuizResponse;
import com.example.kzh.entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query(value = """
                    SELECT new com.example.kzh.dto.response.QuizResponse(
                               q.id,
                               count(passed_q) > 0,
                               q.title,
                               CAST(COALESCE(CEIL(AVG(passed_q.average)), 0) AS integer),
                               CAST(q.level AS integer) ,
                               COALESCE(count(qq), 0))
                    FROM Quiz q
                         LEFT JOIN QuizQuestions qq ON qq.quiz.id = q.id
                         LEFT JOIN PassedQuiz passed_q
                                   ON q.id = passed_q.quiz.id AND (:userId IS NULL OR passed_q.user.id = :userId)
                         LEFT JOIN Question ques ON ques.id = qq.question.id AND (:topics IS NULL OR ques.id IN :topics)
                    WHERE (:searchText IS NULL OR (:searchText LIKE '%' || q.title || '%' OR :searchText LIKE '%' || q.description || '%'))
                    GROUP BY q.title, q.id
                    HAVING (:status IS NULL OR (:status = true AND count(passed_q) > 0))
                       AND (:difficulty IS NULL OR (:difficulty = CAST(COALESCE(CEIL(AVG(passed_q.average)), 0) AS integer)))
            """)
    Page<QuizResponse> findAllByFilters(Long userId, String searchText, Boolean status, Integer difficulty, List<Long> topics, Pageable pageable);


    Quiz findTopByOrderByIdDesc();

    Quiz findTopByOrderById();


}
