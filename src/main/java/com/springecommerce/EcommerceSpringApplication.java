package com.springecommerce;

import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Role;
import com.springecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcommerceSpringApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(EcommerceSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Customer adminAccount = customerRepository.findByRole(Role.ADMIN);
        if(adminAccount == null){
            Customer customer = Customer.builder()
                    .email("admin@mail.com")
                    .firstName("Admin")
                    .lastName("Admin")
                    .role(Role.ADMIN)
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .build();
            customerRepository.save(customer);
        }
    }
}
