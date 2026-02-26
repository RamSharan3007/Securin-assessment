package com.example.demo.util;

import java.io.InputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.RecipeEntity;
import com.example.demo.service.RecipeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {
    private final RecipeService service;

    public DataLoader(RecipeService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/US_recipes_null.json");
        
        try {
            JsonNode root = mapper.readTree(is);
           
            if (root.isObject()) {
                root.fields().forEachRemaining(entry -> {
                    RecipeEntity recipe = mapper.convertValue(entry.getValue(), RecipeEntity.class);
                    service.saveRecipe(recipe);
                });
            } else if (root.isArray()) {
                for (JsonNode node : root) {
                    service.saveRecipe(mapper.convertValue(node, RecipeEntity.class));
                }
            }
            System.out.println(">>> SUCCESS: Data loaded into MySQL.");
        } catch (Exception e) {
            System.err.println(">>> ERROR: Check JSON path or format: " + e.getMessage());
        }
    }
}