
package com.richard.commerce_api.catalog.entity;

import com.richard.commerce_api.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, length = 64)
    private String sku;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public static Product create(
            String name,
            String description,
            String sku,
            BigDecimal price
    ){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Product name is required");
        }

        if(sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("Sku is required");
        }

        if(price == null || price.signum() <0) {
            throw new IllegalArgumentException("Price must not be null");
        }
        Product product = new Product();

            product.name = name.trim();
            product.description = description == null ? null : description.trim();
            product.sku = sku.trim();
            product.price = price;
       return product;


    }

    public void update(
            String name,
            String description,
            String sku,
            BigDecimal price
    ){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Product name is required");
        }

        if(sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("Sku is required");
        }

        if(price == null || price.signum() <0) {
            throw new IllegalArgumentException("Price must be zero or greater");
        }

        this.name = name.trim();
        this.description = description == null ? null : description.trim();
        this.sku = sku.trim();
        this.price = price;
    }

    public void assignCategory(Category category) {
        this.category = Objects.requireNonNull(
                category,
                "Product category is required"
        );

    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }


}
