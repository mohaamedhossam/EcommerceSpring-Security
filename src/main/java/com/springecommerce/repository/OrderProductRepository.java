package com.springecommerce.repository;

import com.springecommerce.dto.ProductQuantityDTO;
import com.springecommerce.entity.Order;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    Optional<OrderProduct> findByOrderAndProduct(Order order, Product product);

    @Modifying
    @Query("UPDATE OrderProduct o SET o.quantity = :quantity WHERE o.orderProductId = :id")
    void updateQuantityByOrderProductId(@Param("id") Long orderProductId, @Param("quantity") Integer quantity);

    @Modifying
    @Query("DELETE FROM OrderProduct o WHERE o.order = :order AND o.product = :product")
    void deleteByOrderAndProduct(@Param("order") Order order, @Param("product") Product product);

    @Query("SELECT op FROM OrderProduct op WHERE op.order.orderId = :orderId")
    List<OrderProduct> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT new com.springecommerce.dto.ProductQuantityDTO(op.product, op.quantity) FROM OrderProduct op WHERE op.order.orderId = :orderId")
    List<ProductQuantityDTO> findProductsAndQuantitiesByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Query("DELETE FROM OrderProduct op WHERE op.order.orderId = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);
}
