package com.lostfound.lostfound.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String password;

     private String role;


 @OneToMany(mappedBy = "user",  cascade =  CascadeType.PERSIST)
 private List<Item> items;

 @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
 private List<Report> reports;


    // private boolean verified;
    // private String verificationToken;
    // private String resetToken;
    // private Long tokenExpiry;
    // private String profilePictureUrl;
    // private String phoneNumber;
    // private String address;
    // private String fullName;
    // private String bio;
    // private String socialMediaLinks;
    // private String preferences;





}
