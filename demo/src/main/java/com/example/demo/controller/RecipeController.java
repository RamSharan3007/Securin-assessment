package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RecipeEntity;
import com.example.demo.repository.RecipeRepository;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeRepository repository;

    @GetMapping("/top")
    public ResponseEntity<Map<String, Object>> getTopRecipes(@RequestParam(defaultValue = "5") int limit) {
       
        List<RecipeEntity> topRecipes = repository.findByOrderByRatingDesc(PageRequest.of(0, limit));
        
        Map<String, Object> response = new HashMap<>();
        response.put("data", topRecipes); 
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody RecipeEntity recipe) {
        
        if (recipe.getTitle() == null || recipe.getCuisine() == null) {
            return ResponseEntity.badRequest().body("Title and Cuisine are required.");
        }
        
        recipe.setTotal_time((recipe.getPrep_time() != null ? recipe.getPrep_time() : 0) + 
                            (recipe.getCook_time() != null ? recipe.getCook_time() : 0));
        return ResponseEntity.ok(repository.save(recipe));
    }
}