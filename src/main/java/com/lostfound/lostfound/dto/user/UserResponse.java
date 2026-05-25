package com.lostfound.lostfound.dto.user;

import com.lostfound.lostfound.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {
    
    private Long id;
    private String name;
    private Role role;
    private String email;
    
}
