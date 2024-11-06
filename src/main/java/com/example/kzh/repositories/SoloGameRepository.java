package com.example.kzh.repositories;

import com.example.kzh.entities.SoloGame;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoloGameRepository extends MongoRepository<SoloGame, String> {
}
