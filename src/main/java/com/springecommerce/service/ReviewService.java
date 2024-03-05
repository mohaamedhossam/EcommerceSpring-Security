package com.springecommerce.service;

import com.springecommerce.entity.Review;
import com.springecommerce.error.AlreadyReviewedException;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.error.ProductNotFoundException;
import java.util.List;

public interface ReviewService {
    Review addReview(Long customerId, Long productId, String comment) throws CustomerNotFoundException, ProductNotFoundException, AlreadyReviewedException;

    List<Review> getAllReviewsForProduct(Long productId) throws ProductNotFoundException;
}
