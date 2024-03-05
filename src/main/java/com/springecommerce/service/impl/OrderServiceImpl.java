package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.error.CustomerAlreadyOwnUnconfimedOrderException;
import com.springecommerce.error.EmpytCartException;
import com.springecommerce.repository.OrderRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order addOrder(Customer customer) {

        Order order = Order.builder()
                .customer(customer)
                .build();
        return orderRepository.save(order);
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getCurrentCustomerOrder(Long customerId) {
        return orderRepository.findFirstByCustomer_CustomerIdAndConfirmedFalse(customerId);
    }

    @Override
    public void updateOrderAmount(Long orderId, int amount) {
        orderRepository.updateOrderAmount(orderId,amount);
    }

    @Override
    @Transactional
    public void computeAndUpdateTotalAmount(Order order) {
        int totalAmount = 0;
        List<OrderProduct> orderProducts = order.getOrderProducts();
        for (OrderProduct orderProduct : orderProducts) {
            totalAmount += orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order confirmOrder(Long customerId, String deliveryAddress) throws EmpytCartException {
        Order order = getCurrentCustomerOrder(customerId);
        if(order.getOrderProducts().isEmpty()){
            throw new EmpytCartException("Cannot Confirm Order without adding products");
        }
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderDate(LocalDate.now());
        order.setExpectedDeliveryDate(LocalDate.now().plusDays(7));
        order.setConfirmed(true);

        Order confirmedOrder = orderRepository.save(order);

        addOrder(order.getCustomer());

        return confirmedOrder;
    }

}