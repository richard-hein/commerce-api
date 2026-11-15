package com.richard.commerce_api.category.entity;


import com.richard.commerce_api.catalog.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();



    @PrePersist
    protected void onCreate(){
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = Instant.now();}


    public static Category create(
            String name,
            String description
    ){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name is required");
        }

        Category category = new Category();
        category.name = name.trim();
        category.description = description == null ? null : description.trim();
        return category;
    }

    public void update(String name, String description){
        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("Category name is required");
        }

        this.name = name.trim();
        this.description = description == null ? null : description.trim();
    }




}
