package com.richard.commerce_api.catalog.service;

import com.richard.commerce_api.catalog.dto.response.ProductResponse;
import com.richard.commerce_api.catalog.entity.Product;
import com.richard.commerce_api.catalog.exception.ProductAlreadyExistsException;
import com.richard.commerce_api.catalog.exception.ProductNotFoundException;
import com.richard.commerce_api.catalog.repository.ProductRepository;
import com.richard.commerce_api.catalog.specification.ProductSpecification;
import com.richard.commerce_api.category.entity.Category;
import com.richard.commerce_api.category.exception.CategoryNotFoundException;
import com.richard.commerce_api.category.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable){
        if(minPrice != null && maxPrice !=null && minPrice.compareTo(maxPrice) >0 ){
            throw new IllegalArgumentException(
                    "Minimum price cannot be greater than maximum"
            );
        }
        Specification<Product> spec = (root,query,cb) -> cb.conjunction();

        if(name != null && !name.isBlank()){
            spec = spec.and(
                    ProductSpecification.hasName(name)
            );
        }

        if(minPrice !=null ){
            spec = spec.and(
                    ProductSpecification.hasMinPrice(minPrice)
            );
        }

        if(maxPrice !=null) {
            spec = spec.and(
                    ProductSpecification.hasMaxPrice(maxPrice)
            );

        }
        return productRepository.findAll(spec, pageable)
                .map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException(id));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse createProduct(
            String name,
            String description,
            String sku,
            BigDecimal price,
            UUID categoryId
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException(categoryId));

        String normalizedSku = sku.trim();
        if(productRepository.existsBySku(normalizedSku)){
            throw new ProductAlreadyExistsException(normalizedSku);
        }

        Product product =Product.create(name,description,normalizedSku, price);
        product.assignCategory(category);

        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(
            UUID id,
            String name,
            String description,
            String sku,
            BigDecimal price,
            UUID categoryId
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException(categoryId));

        String normalizedSku = sku.trim();
        if(productRepository.existsBySkuAndIdNot(normalizedSku, id)){
            throw new ProductAlreadyExistsException(normalizedSku);
        }

        product.update(name, description, normalizedSku, price);
        product.assignCategory(category);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(UUID id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));

        productRepository.delete(product);
    }


}
