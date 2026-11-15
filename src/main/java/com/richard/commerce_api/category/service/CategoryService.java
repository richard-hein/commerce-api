package com.richard.commerce_api.category.service;

import com.richard.commerce_api.catalog.repository.ProductRepository;
import com.richard.commerce_api.category.dto.response.CategoryResponse;
import com.richard.commerce_api.category.entity.Category;
import com.richard.commerce_api.category.exception.CategoryAlreadyExistsException;
import com.richard.commerce_api.category.exception.CategoryInUseException;
import com.richard.commerce_api.category.exception.CategoryNotFoundException;
import com.richard.commerce_api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Transactional(readOnly = true)
    public Page<CategoryResponse> getCategories(Pageable pageable){
        return categoryRepository.findAll(pageable)
                .map(CategoryResponse::from);
    }


    @Transactional
    public Category createCategory(String name,String description){
      String normalizedName = name.trim();

      if(categoryRepository.existsByName(normalizedName)){
          throw new CategoryAlreadyExistsException(
                 normalizedName
          );
      }

       Category category = Category.create(normalizedName,description);
       return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategory(UUID id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(id));
        return CategoryResponse.from(category);
    }

    @Transactional
    public CategoryResponse updateCategory(
            UUID id,
            String name,
            String description
    ){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(id));

        category.update(name,description);
        return CategoryResponse.from(category);
    }

    @Transactional
    public void deleteCategory(UUID id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(id));

        if(productRepository.existsByCategoryId(id)){
            throw new CategoryInUseException(id);
        }

        categoryRepository.delete(category);
    }
}
