package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody RecipeEntity recipe) {
        if (recipe.getTitle() == null || recipe.getCuisine() == null || 
            recipe.getPrep_time() == null || recipe.getCook_time() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Mandatory fields missing"));
        }

        // Logical requirement: Calculate total_time
        recipe.setTotal_time(recipe.getPrep_time() + recipe.getCook_time());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(recipe));
    }

    @GetMapping("/top")
    public ResponseEntity<Map<String, Object>> getTopRecipes(@RequestParam(defaultValue = "5") int limit) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", repository.findByOrderByRatingDesc(PageRequest.of(0, limit)));
        return ResponseEntity.ok(response);
    }
}