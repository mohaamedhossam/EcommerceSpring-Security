package com.springecommerce.service.impl;

import com.springecommerce.entity.Product;
import com.springecommerce.error.ProductNotFoundException;
import com.springecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    ProductServiceImplTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setProductId(1L);
        product.setName("Test Product");
        product.setCategory("Test Category");
        product.setPrice(100);

        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        Assertions.assertEquals(1L, savedProduct.getProductId());
        Assertions.assertEquals("Test Product", savedProduct.getName());
        Assertions.assertEquals("Test Category", savedProduct.getCategory());
        Assertions.assertEquals(100, savedProduct.getPrice());
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", "Category a", 100),
                new Product(2L, "Product 2", "Category b", 200));

        when(productRepository.findAll()).thenReturn(products);

        List<Product> allProducts = productService.getAllProducts();

        Assertions.assertEquals(1L, allProducts.get(0).getProductId());
        Assertions.assertEquals(2L, allProducts.get(1).getProductId());
        Assertions.assertEquals(2, allProducts.size());
    }

    @Test
    void testGetProductById() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product(1L, "Product 1", "Category a", 100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(productId);

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(productId, foundProduct.getProductId());
        Assertions.assertEquals("Product 1", foundProduct.getName());
    }

    @Test
    void whenGetProductById_thenProductNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    public void testUpdateProductById() throws ProductNotFoundException {
        Product existingProduct = new Product(1L, "Existing Product", "Category a", 100);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setCategory("Category b");
        updatedProduct.setPrice(200);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        productService.updateProductById(1L, updatedProduct);
        Assertions.assertEquals(updatedProduct, existingProduct);
    }

    @Test
    @DisplayName("Passing all arguments with null for search method")
    public void testSearchProductsWithNullArguments() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", "Category a", 100),
                new Product(2L, "Product 2", "Category b", 200)
        );
        when(productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndPriceBetween("", "", 0, Integer.MAX_VALUE)).thenReturn(products);
        List<Product> result = this.productService.searchProducts(null, null, null, null);
        Assertions.assertEquals(products, result);
    }
}
