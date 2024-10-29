package com.example.kzh.repositories;

import com.example.kzh.dto.response.ModulesResponse;
import com.example.kzh.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ModuleRepository extends JpaRepository<Module, Long> {

    //todo: rewrite this query
    @Query(value = """
            SELECT new com.example.kzh.dto.response.ModulesResponse(
               m.id AS moduleId,
               m.title AS moduleName,
               ARRAY_AGG(t.id) AS topicId,
               ARRAY_AGG(t.title) AS topicName
            )
            FROM Module m
            LEFT JOIN Topic t ON m.id = t.module.id
            GROUP BY m.id, m.title
                """, nativeQuery = true)
    List<ModulesResponse> findAllModulesAndTheirTopics();

}
