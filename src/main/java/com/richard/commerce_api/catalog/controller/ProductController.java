package com.richard.commerce_api.catalog.controller;

import com.richard.commerce_api.catalog.dto.request.CreateProductRequest;
import com.richard.commerce_api.catalog.dto.response.ProductResponse;
import com.richard.commerce_api.catalog.entity.Product;
import com.richard.commerce_api.catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable) {

        Page<Product> products =productService.searchProducts(
                name,
                minPrice,
                maxPrice,
                pageable
        );

        Page<ProductResponse> response = products.map((ProductResponse::from));

        return ResponseEntity.ok(response);

    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id){
        Product product = productService.getProduct(id);
        return ResponseEntity.ok()
                .body(ProductResponse.from(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request){
        Product product = productService.createProduct(
                request.name(),
                request.description(),
                request.sku(),
                request.price()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductResponse.from(product));

    }

}
