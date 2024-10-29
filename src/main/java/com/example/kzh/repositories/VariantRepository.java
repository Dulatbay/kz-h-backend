package com.example.kzh.repositories;

import com.example.kzh.entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long> {

    @Query(value = """
            SELECT*
            FROM variant AS v
            WHERE v.content = :content
            """, nativeQuery = true)
    Optional<Variant> findByContent(String content);
}
