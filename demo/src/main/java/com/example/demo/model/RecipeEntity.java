package com.example.demo.model;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recipes")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String cuisine;
    private Float rating;

    @JsonProperty("prep_time")
    private Integer prep_time;

    @JsonProperty("cook_time")
    private Integer cook_time;

    @JsonProperty("total_time")
    private Integer total_time;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> nutrients;

    private String serves;
}