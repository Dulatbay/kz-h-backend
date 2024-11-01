package com.example.kzh.repositories;

import com.example.kzh.entities.Module;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByDeletedIsFalse(Sort sort);
}
