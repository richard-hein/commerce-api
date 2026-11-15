package com.richard.commerce_api.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @NotBlank(message = "Category name can't be empty")
        @Size(max=100)
        String name,

        @Size(max=2000)
        String description
) {
}
