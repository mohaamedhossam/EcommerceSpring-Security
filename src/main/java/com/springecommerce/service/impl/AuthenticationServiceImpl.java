package com.springecommerce.service.impl;

import com.springecommerce.dto.JwtAuthenticationResponse;
import com.springecommerce.dto.RefreshTokenRequest;
import com.springecommerce.dto.SignUpRequest;
import com.springecommerce.dto.SigninRequest;
import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Role;
import com.springecommerce.repository.CustomerRepository;
import com.springecommerce.service.AuthenticationService;
import com.springecommerce.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public Customer signub(SignUpRequest signUpRequest){

        Customer customer = Customer.builder()
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        return customerRepository.save(customer);
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));

        var customer = customerRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or Password"));
        var jwt = jwtService.generateToken(customer);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), customer);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        Customer customer = customerRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), customer)){
            var jwt = jwtService.generateToken(customer);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }
}
