package com.richard.commerce_api.category.controller;

import com.richard.commerce_api.category.dto.request.CreateCategoryRequest;
import com.richard.commerce_api.category.dto.request.UpdateCategoryRequest;
import com.richard.commerce_api.category.dto.response.CategoryResponse;
import com.richard.commerce_api.category.entity.Category;
import com.richard.commerce_api.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(
            Pageable pageable
    ){
        Page<CategoryResponse> response = categoryService.getCategories(pageable);
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request){
        Category category = categoryService.createCategory(
                request.name(),
                request.description()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryResponse.from(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getCategory(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest request, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.updateCategory(
                        id,
                        request.name(),
                        request.description()

                ));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
