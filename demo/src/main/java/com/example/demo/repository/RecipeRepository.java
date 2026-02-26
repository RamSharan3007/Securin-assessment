package com.example.demo.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.RecipeEntity;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {
    
    List<RecipeEntity> findByOrderByRatingDesc(Pageable pageable);
}