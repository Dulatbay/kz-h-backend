package com.example.kzh.repositories;

import com.example.kzh.entities.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface TopicRepository extends MongoRepository<Topic, String> {
    Set<Topic> findByIdIn(Set<String> ids);
}
