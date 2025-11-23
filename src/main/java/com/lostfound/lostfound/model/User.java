package com.lostfound.lostfound.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Table(name ="users")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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



// No cascade REMOVE: we don't want deleting user to delete items  
   @OneToMany(mappedBy = "user",  fetch = FetchType.LAZY)
   @JsonIgnore // to prevent recursion when serializing user  
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
