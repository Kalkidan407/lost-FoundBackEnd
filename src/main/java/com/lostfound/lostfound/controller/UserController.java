package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.dto.user.UserRequest;
import com.lostfound.lostfound.dto.user.UserResponse;
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
@ResponseStatus(HttpStatus.CREATED)
  public UserResponse createUser(@Valid @RequestBody UserRequest user){
    return userService.createUser(user);
  }


@GetMapping
@ResponseStatus(HttpStatus.OK)
   public List<UserResponse> getAlUsers() {
       return userService.getAllUser();
   }

@GetMapping("/id/{id}")
@ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUsersById(id);
               
    }

@GetMapping("/username/{name}")
@ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByUsername(@PathVariable String name) {
        return userService.getUserByUsername(name);
  }

  @GetMapping("/password/{username}")
  @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserPassword(@PathVariable String username) {
        return userService.getUserPassword(username);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUserById(@PathVariable Long id){
    userService.deleteUserById(id);
  }
    
    
   

    
    
}
