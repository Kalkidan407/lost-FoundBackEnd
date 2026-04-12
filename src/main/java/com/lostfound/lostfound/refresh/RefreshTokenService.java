package com.lostfound.lostfound.refresh;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRepository refreshTokenRepository;


    
    public String generateRefreshToken() {
    return UUID.randomUUID().toString();
}


 
    
}
