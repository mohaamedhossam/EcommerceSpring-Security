package com.springecommerce.repository;

import com.springecommerce.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findFirstByCustomerCustomerIdAndProductProductId(Long customerId, Long productId);

    Optional<List<Review>> findByProductProductId(Long productId);
}
