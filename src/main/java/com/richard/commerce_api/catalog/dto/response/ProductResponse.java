package com.richard.commerce_api.catalog.dto.response;

import com.richard.commerce_api.catalog.entity.Product;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record ProductResponse(
 UUID id,
 String name,
 String description,
 String sku,
 BigDecimal price,
 boolean active
) {
    public static ProductResponse from(Product product){
//        it returns the original non-null reference, or throws NullPointerException otherwise.
        Objects.requireNonNull(product, "Product cannot be null");

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getPrice(),
                product.isActive()
        );
    }
}
