package com.commerceapp.commerceapp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.commerceapp.commerceapp.DTO.AuthRequest;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                  JwtService jwtService,
                                  UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }
    
    public String generateToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            return jwtService.generateToken(userDetails); 
        }
        throw new UsernameNotFoundException("Invalid username: " + authRequest.getUsername());
    }
}
