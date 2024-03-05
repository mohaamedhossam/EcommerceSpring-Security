package com.springecommerce.service;

import com.springecommerce.entity.Review;
import com.springecommerce.error.CustomException;

import java.util.List;

public interface ReviewService {
    Review addReview(Long customerId, Long productId, String comment) throws CustomException;

    List<Review> getAllReviewsForProduct(Long productId) throws CustomException;
}
