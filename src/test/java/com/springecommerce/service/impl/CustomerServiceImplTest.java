package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.repository.CustomerRepository;
import com.springecommerce.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private CustomerServiceImpl customerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCustomer_Success() throws CustomerNotFoundException {
        Order order = new Order();
        when(this.orderService.addOrder(any())).thenReturn(order);

        Customer customer = Customer.builder().customerId(1L).email("hoss@gmail.com").firstName("hoss").lastName("mostafa").password("1234").build();
        when(this.customerRepository.save(any())).thenReturn(customer);

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertEquals("hoss@gmail.com", savedCustomer.getEmail());
        assertEquals(order, savedCustomer.getOrders().get(0));
    }

    @Test
    void saveCustomer_DuplicateEmail() {
        Customer customer = Customer.builder().email("duplicate@gmail.com").build();
        when(customerRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> {
            customerService.saveCustomer(customer);
        });
    }

    @Test
    void login_Success() throws CustomerNotFoundException {
        Customer customer = Customer.builder().email("test@gmail.com").password("password").build();
        when(customerRepository.findCustomerByEmailAndPassword("test@gmail.com", "password")).thenReturn(Optional.of(customer));

        Customer loggedInCustomer = this.customerService.login("test@gmail.com", "password");

        assertEquals("test@gmail.com", loggedInCustomer.getEmail());
    }

    @Test
    void login_CustomerNotFound() {
        when(customerRepository.findCustomerByEmailAndPassword("notfound@gmail.com", "password")).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.login("email@example.com", "password");
        });
    }
}
