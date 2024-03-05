package com.springecommerce.service;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.error.EmpytCartException;
import java.util.List;

public interface OrderService {
    Order addOrder(Customer customer);

    List<Order> getAllOrders();

    Order getCurrentCustomerOrder(Long customerId);

    void updateOrderAmount(Long orderId, int amount);

    void computeAndUpdateTotalAmount(Order order);

    Order confirmOrder(Long customerId, String deliveryAddress) throws EmpytCartException;
}
