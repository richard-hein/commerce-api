package com.richard.commerce_api.catalog.controller;

import com.richard.commerce_api.catalog.dto.request.CreateProductRequest;
import com.richard.commerce_api.catalog.dto.request.UpdateProductRequest;
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

        Page<ProductResponse> response = productService.searchProducts(
                name,minPrice,maxPrice, pageable
        );

        return ResponseEntity.ok(response);

    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProduct(id));

    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request){
        return ResponseEntity.ok(productService.createProduct(
                request.name(),
                request.description(),
                request.sku(),
                request.price(),
                request.categoryId()
        ));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request
    ){
        return ResponseEntity.ok(productService.updateProduct(
                id,
                request.name(),
                request.description(),
                request.sku(),
                request.price(),
                request.categoryId()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}
