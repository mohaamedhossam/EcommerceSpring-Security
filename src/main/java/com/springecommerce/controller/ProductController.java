package com.springecommerce.controller;

import com.springecommerce.entity.Product;
import com.springecommerce.error.CustomException;
import com.springecommerce.service.ProductService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/products"})
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product saveProduct(@RequestBody @Valid Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping({"/{id}"})
    public Product getProductById(@PathVariable("id") Long productId) throws CustomException {
        return productService.getProductById(productId);
    }

    @PutMapping({"/{id}"})
    public Product updateProductById(@PathVariable("id") Long productId,
                                     @RequestBody Product product) throws CustomException {
        return productService.updateProductById(productId, product);
    }

    @GetMapping({"/search"})
    public List<Product> searchProducts(@RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String category,
                                        @RequestParam(required = false) Integer minPrice,
                                        @RequestParam(required = false) Integer maxPrice) {
        return productService.searchProducts(keyword, category, minPrice, maxPrice);
    }
}
