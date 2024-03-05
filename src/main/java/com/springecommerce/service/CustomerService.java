package com.springecommerce.service;

import com.springecommerce.entity.Customer;
import com.springecommerce.error.CustomException;

public interface CustomerService {
    Customer saveCustomer(Customer customer) throws CustomException;

    Customer login(String email, String password) throws CustomException;

    Customer getCustomerById(Long customerId) throws CustomException;

    Long getUnconfirmedOrderId(Customer customer);

    Boolean alreadyOwnUnconfirmedOrder(Customer customer) throws CustomException;
}
