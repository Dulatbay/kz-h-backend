package com.example.kzh.repositories;

import com.example.kzh.entities.QuizQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepository extends MongoRepository<QuizQuestion, String> {
}
