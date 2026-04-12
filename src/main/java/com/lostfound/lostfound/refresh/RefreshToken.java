package com.lostfound.lostfound.refresh;

import java.time.LocalDateTime;

import com.lostfound.lostfound.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;


    private LocalDateTime expireDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}
