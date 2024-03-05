package com.springecommerce.controller;

import com.springecommerce.entity.Customer;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    public CustomerController() {
    }

    @PostMapping({"/register"})
    public Customer register(@RequestBody @Valid Customer customer) throws CustomerNotFoundException {
        return customerService.saveCustomer(customer);
    }

    @GetMapping({"/login"})
    public Customer login(@RequestParam String email,
                          @RequestParam String password) throws CustomerNotFoundException {
        return customerService.login(email, password);
    }
}
