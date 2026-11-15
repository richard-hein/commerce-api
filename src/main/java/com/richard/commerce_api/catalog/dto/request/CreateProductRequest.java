package com.richard.commerce_api.catalog.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(
        @NotBlank(message = "Product name cannot be empty")
        @Size(max= 150)
        String name,

        @Size(max = 2000)
        String description,

        @NotBlank(message = "Sku cannot be empty")
        @Size(max = 64)
        String sku,

        @NotNull(message="Price cannot be null")
        @DecimalMin("0.00") // reject negative prices
        @Digits(integer = 10, fraction =2) // enforces up to 10 integer digits and 2 decimal digits
        BigDecimal price,

        @NotNull(message="Category id cannot be null")
        UUID categoryId
) {
}
