package com.springecommerce.service;

import com.springecommerce.dto.ProductQuantityDTO;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.error.OrderProductNotFoundException;
import com.springecommerce.error.ProductNotFoundException;
import java.util.List;

public interface OrderProductService {
    OrderProduct addToCart(Long customerId, Long productId, Integer quantity) throws ProductNotFoundException, CustomerNotFoundException;

    void removeFromCart(Long customerId, Long productId) throws CustomerNotFoundException, ProductNotFoundException, OrderProductNotFoundException;

    List<ProductQuantityDTO> openCart(Long customerId) throws CustomerNotFoundException;

    void clearCart(Long customerId) throws CustomerNotFoundException;
}
