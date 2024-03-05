package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.error.CustomException;
import com.springecommerce.repository.OrderRepository;
import com.springecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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
    public Order confirmOrder(Long customerId, String deliveryAddress) throws CustomException {
        Order order = getCurrentCustomerOrder(customerId);
        if(order.getOrderProducts().isEmpty()){
            throw new CustomException("Cannot Confirm Order without adding products", HttpStatus.NOT_ACCEPTABLE);
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