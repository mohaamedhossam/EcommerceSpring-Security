package com.springecommerce.controller;

import com.springecommerce.entity.Order;
import com.springecommerce.error.EmpytCartException;
import com.springecommerce.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/orders"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderController() {
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping({"/confirm"})
    public Order confirmOrder(@RequestParam Long customerId,
                              @RequestParam String deliveryAddress) throws EmpytCartException {
        return orderService.confirmOrder(customerId, deliveryAddress);
    }
}
