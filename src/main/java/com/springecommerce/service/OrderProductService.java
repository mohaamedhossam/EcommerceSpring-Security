package com.springecommerce.service;

import com.springecommerce.dto.ProductQuantityDTO;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.error.CustomException;

import java.util.List;

public interface OrderProductService {
    OrderProduct addToCart(Long customerId, Long productId, Integer quantity) throws CustomException;

    void removeFromCart(Long customerId, Long productId) throws CustomException;

    List<ProductQuantityDTO> openCart(Long customerId) throws CustomException;

    void clearCart(Long customerId) throws CustomException;
}
