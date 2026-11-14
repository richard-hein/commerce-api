
package com.richard.commerce_api.catalog.repository;

import com.richard.commerce_api.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {

//    Query Derivation
    Page<Product> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    ) ;

    Page<Product> findByPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

}
