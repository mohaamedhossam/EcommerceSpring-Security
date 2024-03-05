package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Product;
import com.springecommerce.entity.Review;
import com.springecommerce.error.CustomException;
import com.springecommerce.repository.ReviewRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.ProductService;
import com.springecommerce.service.ReviewService;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, CustomerService customerService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public Review addReview(Long customerId, Long productId, String comment) throws CustomException {
        Customer customer = this.customerService.getCustomerById(customerId);
        Product product = this.productService.getProductById(productId);
        Optional<Review> reviewDB = this.reviewRepository.findFirstByCustomerCustomerIdAndProductProductId(customerId, productId);
        if (reviewDB.isPresent()) {
            throw new CustomException("Already reviewed before",HttpStatus.NOT_ACCEPTABLE);
        } else {
            Review review = Review.builder().customer(customer).product(product).comment(comment).build();
            return reviewRepository.save(review);
        }
    }

    public List<Review> getAllReviewsForProduct(Long productId) throws CustomException {
        return reviewRepository.findByProductProductId(productId)
                .orElseThrow(() -> new CustomException("No such product with this id", HttpStatus.NOT_FOUND));
    }
}
