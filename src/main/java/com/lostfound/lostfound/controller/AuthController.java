package com.lostfound.lostfound.controller;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.*;
import com.lostfound.lostfound.model.Role;
import com.lostfound.lostfound.model.User;
import com.lostfound.lostfound.refresh.RefreshToken;
import com.lostfound.lostfound.refresh.RefreshTokenRepository;
import com.lostfound.lostfound.refresh.RefreshTokenService;
import com.lostfound.lostfound.repository.UserRepository;
import com.lostfound.lostfound.security.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "auth", description = "Register & Login APIs")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public String register( @RequestBody RegisterRequest request ) {

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
    public AuthResponse login( @RequestBody LoginRequest request ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken =
                jwtService.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        

        return new AuthResponse(accessToken, refreshToken.getToken());
        
    }

    @PostMapping("/refresh")
public AuthResponse refresh(@RequestBody RefreshRequest request) {
   
    RefreshToken token = refreshTokenService.verifyToken(request.getRefreshToken());

    String newAccessToken = jwtService.generateToken(token.getUser());

    return new AuthResponse(newAccessToken, request.getRefreshToken());
}


  



  
}


