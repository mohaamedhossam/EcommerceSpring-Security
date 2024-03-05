package com.springecommerce.service;

import com.springecommerce.entity.Customer;
import com.springecommerce.error.CustomerAlreadyOwnUnconfimedOrderException;
import com.springecommerce.error.CustomerNotFoundException;

public interface CustomerService {
    Customer saveCustomer(Customer customer) throws CustomerNotFoundException;

    Customer login(String email, String password) throws CustomerNotFoundException;

    Customer getCustomerById(Long customerId) throws CustomerNotFoundException;

    Long getUnconfirmedOrderId(Customer customer);

    Boolean alreadyOwnUnconfirmedOrder(Customer customer) throws CustomerAlreadyOwnUnconfimedOrderException;
}
