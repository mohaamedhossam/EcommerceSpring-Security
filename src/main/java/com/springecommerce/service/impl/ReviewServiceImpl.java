package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Product;
import com.springecommerce.entity.Review;
import com.springecommerce.error.AlreadyReviewedException;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.error.ProductNotFoundException;
import com.springecommerce.repository.ReviewRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.ProductService;
import com.springecommerce.service.ReviewService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    public Review addReview(Long customerId, Long productId, String comment) throws CustomerNotFoundException, ProductNotFoundException, AlreadyReviewedException {
        Customer customer = this.customerService.getCustomerById(customerId);
        Product product = this.productService.getProductById(productId);
        Optional<Review> reviewDB = this.reviewRepository.findFirstByCustomerCustomerIdAndProductProductId(customerId, productId);
        if (reviewDB.isPresent()) {
            throw new AlreadyReviewedException("Already reviewed before");
        } else {
            Review review = Review.builder().customer(customer).product(product).comment(comment).build();
            return reviewRepository.save(review);
        }
    }

    public List<Review> getAllReviewsForProduct(Long productId) throws ProductNotFoundException {
        return reviewRepository.findByProductProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("No such product with this id"));
    }
}
