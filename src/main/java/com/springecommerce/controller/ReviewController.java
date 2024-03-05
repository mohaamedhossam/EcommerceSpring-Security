package com.springecommerce.controller;

import com.springecommerce.entity.Review;
import com.springecommerce.error.AlreadyReviewedException;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.error.ProductNotFoundException;
import com.springecommerce.service.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/reviews"})
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestParam(required = true) Long customerId,
                            @RequestParam(required = true) Long productId,
                            @RequestParam(required = true) String comment) throws CustomerNotFoundException, ProductNotFoundException, AlreadyReviewedException {
        return reviewService.addReview(customerId, productId, comment);
    }

    @GetMapping
    public List<Review> getAllReviewsForProduct(@RequestParam(required = true) Long productId) throws ProductNotFoundException {
        return reviewService.getAllReviewsForProduct(productId);
    }
}
