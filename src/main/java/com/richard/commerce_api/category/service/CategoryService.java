package com.richard.commerce_api.category.service;

import com.richard.commerce_api.category.entity.Category;
import com.richard.commerce_api.category.exception.CategoryAlreadyExistsException;
import com.richard.commerce_api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(String name,String description){
      String normalizedName = name.trim();

      if(categoryRepository.existsByName(normalizedName)){
          throw new CategoryAlreadyExistsException(
                 normalizedName
          );
      }

       Category category = Category.create(name,description);
       return categoryRepository.save(category);
    }
}
