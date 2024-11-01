package com.example.kzh.repositories;

import com.example.kzh.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
            SELECT u.*
            FROM kzh_user u
            WHERE u.email = :email AND u.is_deleted = false
            """, nativeQuery = true)
    Optional<User> findByEmail(String email);
}
