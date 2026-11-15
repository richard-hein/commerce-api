
package com.richard.commerce_api.catalog.repository;

import com.richard.commerce_api.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.Optional;
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

    @EntityGraph(attributePaths = "category")
    Page<Product> findAll(
            Specification<Product> spec,
            Pageable pageable
    );

    @EntityGraph(attributePaths = "category")
    Optional<Product> findById(UUID id);

    boolean existsByCategoryId(UUID categoryId);

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, UUID id);

}
