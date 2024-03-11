package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.error.CustomException;
import com.springecommerce.repository.CustomerRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.OrderService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    private final OrderService orderService;

    public CustomerServiceImpl(CustomerRepository customerRepository, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return customerRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        try {
            Customer savedCustomer = customerRepository.save(customer);
            Order order = orderService.addOrder(savedCustomer);
            savedCustomer.setOrders(List.of(order));
            return savedCustomer;
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityViolationException("This email is used before");
        }

    }

    @Override
    public Customer login(String email, String password) throws CustomException {
        Optional<Customer> customer = customerRepository.findCustomerByEmailAndPassword(email, password);
        if (!customer.isPresent()) {
            throw new CustomException("Worng email or Password", HttpStatus.NOT_FOUND);
        }
        return customer.get();
    }

    @Override
    public Customer getCustomerById(Long customerId) throws CustomException {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (!customer.isPresent()) {
            throw new CustomException("No such Customer with this id",HttpStatus.NOT_FOUND);
        }
        return customer.get();
    }

    @Override
    public Long getUnconfirmedOrderId(Customer customer) {
        if (customer.getOrders() != null) {
            for (Order order : customer.getOrders()) {
                if (!order.isConfirmed()) {
                    return order.getOrderId();
                }
            }
        }
        return null;
    }

    @Override
    public Boolean alreadyOwnUnconfirmedOrder(Customer customer) throws CustomException {
        if (getUnconfirmedOrderId(customer) != null) {
            throw new CustomException("Already have unconfirmed Order",HttpStatus.NOT_ACCEPTABLE);
        }
        return false;
    }
}
