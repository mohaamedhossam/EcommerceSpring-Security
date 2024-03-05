package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.entity.Product;
import com.springecommerce.error.CustomException;
import com.springecommerce.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    OrderServiceImplTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrder() {
        Customer customer = new Customer();
        customer.setFirstName("Mohamed");

        Order order = new Order();
        order.setCustomer(customer);

        when(orderRepository.save(ArgumentMatchers.any())).thenReturn(order);
        Order savedOrder = orderService.addOrder(customer);
        assertNotNull(savedOrder);
        assertEquals("Mohamed", savedOrder.getCustomer().getFirstName());
    }

    @Test
    void testGetCurrentCustomerOrder() {
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .customerId(customerId)
                .build();

        Order order = Order.builder()
                .customer(customer)
                .confirmed(false)
                .build();
        when(orderRepository.findFirstByCustomer_CustomerIdAndConfirmedFalse(customerId)).thenReturn(order);

        Order retrievedOrder = orderService.getCurrentCustomerOrder(customerId);

        assertEquals(1L, retrievedOrder.getCustomer().getCustomerId());
        assertFalse(retrievedOrder.isConfirmed());
    }

    @Test
    void testComputeAndUpdateTotalAmount() {
        Product product1 = Product.builder().price(100).build();
        Product product2 = Product.builder().price(200).build();
        Product product3 = Product.builder().price(300).build();
        List<OrderProduct> orderProducts = Arrays.asList(
                OrderProduct.builder().product(product1).quantity(10).build(),
                OrderProduct.builder().product(product2).quantity(1).build(),
                OrderProduct.builder().product(product3).quantity(1).build()
        );
        Order order = Order.builder().orderProducts(orderProducts).build();
        orderService.computeAndUpdateTotalAmount(order);

        assertEquals(1500, order.getTotalAmount());
        verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void testConfirmOrderWithProductsInCart() throws CustomException {

        Product product1 = Product.builder().price(100).build();

        List<OrderProduct> orderProducts = Collections.singletonList(
                OrderProduct.builder().product(product1).quantity(10).build()
        );
        Order order = Order.builder()
                .orderProducts(orderProducts)
                .build();
        Long customerId = 1L;
        String deliveryAddress = "12 test St";

        when(orderRepository.findFirstByCustomer_CustomerIdAndConfirmedFalse(customerId)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        Order confirmedOrder = orderService.confirmOrder(customerId, deliveryAddress);
        assertEquals(deliveryAddress, confirmedOrder.getDeliveryAddress());
        assertEquals(LocalDate.now(), confirmedOrder.getOrderDate());
        assertEquals(LocalDate.now().plusDays(7), confirmedOrder.getExpectedDeliveryDate());
        assertTrue(confirmedOrder.isConfirmed());
    }

    @Test
    void testConfirmOrderWithEmptyCart() {
        Long customerId = 1L;
        String deliveryAddress = "12 test St";

        Order order = new Order();
        order.setOrderProducts(new ArrayList());

        when(orderRepository.findFirstByCustomer_CustomerIdAndConfirmedFalse(customerId)).thenReturn(order);

        assertThrows(CustomException.class, () -> {
            orderService.confirmOrder(customerId, deliveryAddress);
        });
        verify(orderRepository, never()).save(ArgumentMatchers.any());
    }
}
