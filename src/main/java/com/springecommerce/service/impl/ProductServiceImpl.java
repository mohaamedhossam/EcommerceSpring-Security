
package com.springecommerce.service.impl;

import com.springecommerce.entity.Product;
import com.springecommerce.error.ProductNotFoundException;
import com.springecommerce.repository.ProductRepository;
import com.springecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("No such Product with this id");
        }
        return product.get();
    }

    @Override
    public List<Product> searchProducts(String keyword, String category, Integer minPrice, Integer maxPrice) {

        if (keyword == null) {
            keyword = "";
        }
        if (category == null) {
            category = "";
        }
        if (minPrice == null || minPrice < 0) {
            minPrice = 0;
        }
        if (maxPrice == null || maxPrice <= 0) {
            maxPrice = Integer.MAX_VALUE;
        }

        return productRepository
                .findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndPriceBetween(
                        keyword,
                        category,
                        minPrice,
                        maxPrice
                );
    }

    @Override
    public Product updateProductById(Long productId, Product product) throws ProductNotFoundException {
        Optional<Product> productDB = productRepository.findById(productId);
        if (!productDB.isPresent()) {
            throw new ProductNotFoundException("No such Product with this id");
        }
        Product productDB2 = productDB.get();

        if (Objects.nonNull(product.getName()) &&
                !"".equalsIgnoreCase(product.getName())) {
            productDB2.setName(product.getName());
        }
        if (Objects.nonNull(product.getCategory()) &&
                !"".equalsIgnoreCase(product.getCategory())) {
            productDB2.setCategory(product.getCategory());
        }
        if (Objects.nonNull(product.getPrice())) {
            productDB2.setPrice(product.getPrice());
        }
        return productRepository.save(productDB2);

    }
}