package com.springecommerce.controller;

import com.springecommerce.entity.Review;
import com.springecommerce.error.CustomException;
import com.springecommerce.service.ReviewService;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/reviews"})
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Review addReview(@RequestParam(required = true) Long customerId,
                            @RequestParam(required = true) Long productId,
                            @RequestParam(required = true) String comment) throws CustomException {
        return reviewService.addReview(customerId, productId, comment);
    }

    @GetMapping
    public List<Review> getAllReviewsForProduct(@RequestParam(required = true) Long productId) throws CustomException {
        return reviewService.getAllReviewsForProduct(productId);
    }
}
