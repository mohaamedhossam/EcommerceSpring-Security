package com.springecommerce.controller;

import com.springecommerce.entity.Customer;
import com.springecommerce.error.CustomException;
import com.springecommerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping({"/register"})
    public Customer register(@RequestBody @Valid Customer customer) throws CustomException {
        return customerService.saveCustomer(customer);
    }

    @GetMapping({"/login"})
    public Customer login(@RequestParam String email,
                          @RequestParam String password) throws CustomException {
        return customerService.login(email, password);
    }
}
