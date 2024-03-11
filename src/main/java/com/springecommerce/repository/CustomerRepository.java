package com.springecommerce.repository;

import com.springecommerce.entity.Customer;
import java.util.Optional;

import com.springecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findCustomerByEmailAndPassword(String email, String password);

    Customer findByRole(Role role);
}
