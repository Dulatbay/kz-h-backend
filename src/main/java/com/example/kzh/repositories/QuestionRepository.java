package com.example.kzh.repositories;

import com.example.kzh.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
    Page<Question> findAllByIsVerifiedIsTrueOrderByIdDesc(Pageable pageable);
}
