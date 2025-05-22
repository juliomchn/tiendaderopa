package com.mitiendaropa.controller;

import com.mitiendaropa.dto.JwtResponse;
import com.mitiendaropa.dto.LoginRequest;
import com.mitiendaropa.security.JwtUtil;
import com.mitiendaropa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsernameOrEmail());
        final String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
