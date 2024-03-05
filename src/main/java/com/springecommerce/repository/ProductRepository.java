package com.springecommerce.repository;

import com.springecommerce.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndPriceBetween(String name, String category, Integer minPrice, Integer maxPrice);
}
