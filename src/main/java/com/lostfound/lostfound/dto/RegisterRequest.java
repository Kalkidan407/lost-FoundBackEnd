package com.lostfound.lostfound.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
}


