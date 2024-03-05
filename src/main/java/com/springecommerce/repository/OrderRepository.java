package com.springecommerce.repository;

import com.springecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findFirstByCustomer_CustomerIdAndConfirmedFalse(Long customerId);

    @Modifying
    @Query("UPDATE Order o SET o.totalAmount = :amount WHERE o.orderId = :orderId")
    void updateOrderAmount(@Param("orderId") Long orderId, @Param("amount") int amount);
}
