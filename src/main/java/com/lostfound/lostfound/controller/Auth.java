package com.lostfound.lostfound.controller;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.*;
import com.lostfound.lostfound.model.Role;
import com.lostfound.lostfound.model.User;
import com.lostfound.lostfound.repository.UserRepository;
import com.lostfound.lostfound.security.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "Register & Login APIs")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class Auth{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        
        user.setRole(Role.USER);
        userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(request.getEmail());

        return new AuthResponse(token);
    }

}
