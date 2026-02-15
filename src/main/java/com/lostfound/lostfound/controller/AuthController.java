package com.lostfound.lostfound.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.dto.AuthResponse;
import com.lostfound.lostfound.dto.LoginRequest;
import com.lostfound.lostfound.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // AuthController(AuthenticationManager authenticationManager) {
    //     this.authenticationManager = authenticationManager;
    // }

    @PostMapping("/login")
    public AuthResponse login (@RequestBody LoginRequest request) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                 request.getPassword()
            )
        );

        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
    
    
}
