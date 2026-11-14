package com.richard.commerce_api.catalog.service;

import com.richard.commerce_api.catalog.entity.Product;
import com.richard.commerce_api.catalog.exception.ProductNotFoundException;
import com.richard.commerce_api.catalog.repository.ProductRepository;
import com.richard.commerce_api.catalog.specification.ProductSpecification;
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


    @Transactional(readOnly = true)
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> searchProducts(
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
        return productRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Product getProduct(UUID id){
        return productRepository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException(id));
    }

    @Transactional
    public Product createProduct(
            String name,
            String description,
            String sku,
            BigDecimal price
    ) {
        Product product =Product.create(name,description,sku, price);
        return productRepository.save(product);
    }


//    public Product createProduct(@Valid CreateProductRequest productRequest) {
//        return null;
//    }
}
