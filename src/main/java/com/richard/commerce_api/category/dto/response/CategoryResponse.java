package com.richard.commerce_api.category.dto.response;

import com.richard.commerce_api.category.entity.Category;


import java.util.Objects;
import java.util.UUID;

public record CategoryResponse (
      UUID id,
      String name,
      String description
){
    public static CategoryResponse from(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );

    }

}
