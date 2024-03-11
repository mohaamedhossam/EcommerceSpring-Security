package com.springecommerce.controller;

import com.springecommerce.dto.JwtAuthenticationResponse;
import com.springecommerce.dto.RefreshTokenRequest;
import com.springecommerce.dto.SignUpRequest;
import com.springecommerce.dto.SigninRequest;
import com.springecommerce.entity.Customer;
import com.springecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(@RequestBody SignUpRequest signUpRequest){

        return ResponseEntity.ok(authenticationService.signub(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SigninRequest signinRequest){

        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){

        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
