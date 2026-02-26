package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.model.RecipeEntity;
import com.example.demo.repository.RecipeRepository;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    /**
     * Requirement: Logic for POST /recipes
     * Automatically calculates total_time before saving to MySQL.
     */
    public RecipeEntity saveRecipe(RecipeEntity recipe) {
        // Logic: Calculate total_time = prep + cook
        int prep = (recipe.getPrep_time() != null) ? recipe.getPrep_time() : 0;
        int cook = (recipe.getCook_time() != null) ? recipe.getCook_time() : 0;
        
        recipe.setTotal_time(prep + cook);
        
        return repository.save(recipe);
    }

    /**
     * Requirement: Logic for GET /recipes/top
     * Fetches top rated recipes with a limit.
     */
    public List<RecipeEntity> getTopRecipes(int limit) {
        return repository.findByOrderByRatingDesc(PageRequest.of(0, limit));
    }

    /**
     * Helper: Get all recipes for a standard GET request
     */
    public List<RecipeEntity> getAllRecipes() {
        return repository.findAll();
    }
}