package com.springecommerce.controller;

import com.springecommerce.dto.ProductQuantityDTO;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.error.OrderProductNotFoundException;
import com.springecommerce.error.ProductNotFoundException;
import com.springecommerce.service.OrderProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/cart"})
public class CartController {

    @Autowired
    private OrderProductService orderProductService;

    @PostMapping
    public OrderProduct addToCart(@RequestParam Long customerId,
                                  @RequestParam Long productId,
                                  @RequestParam Integer quantity) throws ProductNotFoundException, CustomerNotFoundException {
        return orderProductService.addToCart(customerId, productId, quantity);
    }

    @DeleteMapping
    public String removeFromCart(@RequestParam Long customerId,
                                 @RequestParam Long productId) throws ProductNotFoundException, CustomerNotFoundException, OrderProductNotFoundException {
        orderProductService.removeFromCart(customerId, productId);
        return "DELETED";
    }

    @GetMapping
    public List<ProductQuantityDTO> openCart(@RequestParam Long customerId) throws CustomerNotFoundException {
        return orderProductService.openCart(customerId);
    }

    @DeleteMapping({"/all"})
    public String clearCart(@RequestParam Long customerId) throws CustomerNotFoundException {
        orderProductService.clearCart(customerId);
        return "Cleared";
    }
}
