package com.example.kzh.repositories;

import com.example.kzh.dto.response.QuizResponseDto;
import com.example.kzh.entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

//    @Query("SELECT " +
//            "qz.ID AS quizID," +
//            "qz.title AS quizTitle," +
//            "AVG(q.level) AS avgLevel" +
//            "FROM Quiz qz" +
//            "JOIN QuizQuestions qq ON qz.ID = qq.quizID" +
//            "JOIN Question q ON qq.questionID = q.ID" +
//            "GROUP BY qz.ID, qz.title")
//    QuizResponseDto findAllWithFilters(
//            @Param("difficulty") Integer difficulty,
//            @Param("status") Boolean status,
//            @Param("topics") List<Long> topics,
//            @Param("modules") List<Long> modules,
//            @Param("searchText") String searchText
//    );


}
