package com.springecommerce.service;

import com.springecommerce.dto.JwtAuthenticationResponse;
import com.springecommerce.dto.RefreshTokenRequest;
import com.springecommerce.dto.SignUpRequest;
import com.springecommerce.dto.SigninRequest;
import com.springecommerce.entity.Customer;

public interface AuthenticationService {
    Customer signub(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
