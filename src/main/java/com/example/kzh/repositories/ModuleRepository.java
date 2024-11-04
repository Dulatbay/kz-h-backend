package com.example.kzh.repositories;

import com.example.kzh.entities.KzhModule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends MongoRepository<KzhModule, String> {
}
