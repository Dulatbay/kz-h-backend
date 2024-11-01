package com.example.kzh.repositories;

import com.example.kzh.entities.Topic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByDeletedIsFalse(Sort sort);
}
