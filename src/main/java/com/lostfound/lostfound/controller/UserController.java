package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.model.User;
import com.lostfound.lostfound.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // User-related endpoints can be added here

private final UserService userService;


@PostMapping
  public User createUser(@Valid @RequestBody User user){
    return userService.createUser(user);
  }


@GetMapping
   public List<User> getAlUsers() {
       return userService.getAllUser();
   }

@GetMapping("/id/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUsersById(id).orElse(null);
               
    }

@GetMapping("/username/{name}")
    public User getUserByUsername(@PathVariable String name) {
        return userService.getUserByUsername(name);
  }

  @GetMapping("/password/{username}")
    public String getUserPassword(@PathVariable String username) {
        return userService.getUserPassword(username);
  }

  @DeleteMapping("/{id}")
  public void deleteUserById(@PathVariable Long id){
    userService.deleteUserById(id);
  }
    
    
   

    
    
}
