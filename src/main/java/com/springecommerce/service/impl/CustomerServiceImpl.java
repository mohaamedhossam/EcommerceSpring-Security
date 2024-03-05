package com.springecommerce.service.impl;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.error.CustomerAlreadyOwnUnconfimedOrderException;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.repository.CustomerRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Customer saveCustomer(Customer customer) throws CustomerNotFoundException {

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
    public Customer login(String email, String password) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findCustomerByEmailAndPassword(email, password);
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException("Worng email or Password");
        }
        return customer.get();
    }

    @Override
    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (!customer.isPresent()) {
            throw new CustomerNotFoundException("No such Customer with this id");
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
    public Boolean alreadyOwnUnconfirmedOrder(Customer customer) throws CustomerAlreadyOwnUnconfimedOrderException {
        if (getUnconfirmedOrderId(customer) != null) {
            throw new CustomerAlreadyOwnUnconfimedOrderException("Already have unconfirmed Order");
        }
        return false;
    }
}
