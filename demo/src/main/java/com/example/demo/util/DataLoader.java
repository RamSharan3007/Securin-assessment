package com.example.demo.util;

import com.example.demo.model.RecipeEntity;
import com.example.demo.repository.RecipeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository repository;

    public DataLoader(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/US_recipes_null.json");
        
        JsonNode root = mapper.readTree(is);
        Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            RecipeEntity recipe = mapper.convertValue(entry.getValue(), RecipeEntity.class);
            
            // Pre-calculate total_time if missing during ingestion
            if (recipe.getTotal_time() == null && recipe.getPrep_time() != null && recipe.getCook_time() != null) {
                recipe.setTotal_time(recipe.getPrep_time() + recipe.getCook_time());
            }
            repository.save(recipe);
        }
        System.out.println(">>> Data Ingestion Complete.");
    }
}