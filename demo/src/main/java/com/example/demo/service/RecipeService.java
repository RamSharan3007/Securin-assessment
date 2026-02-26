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

    public RecipeEntity saveRecipe(RecipeEntity recipe) {
        
        if (recipe.getPrep_time() != null && recipe.getCook_time() != null) {
            recipe.setTotal_time(recipe.getPrep_time() + recipe.getCook_time());
        }
        return repository.save(recipe);
    }

    public List<RecipeEntity> getTopRecipes(int limit) {
       
        return repository.findByOrderByRatingDesc(PageRequest.of(0, limit));
    }
}