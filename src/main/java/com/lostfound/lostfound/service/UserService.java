package com.lostfound.lostfound.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.user.UserRequest;
import com.lostfound.lostfound.dto.user.UserResponse;
import com.lostfound.lostfound.model.User;
import com.lostfound.lostfound.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private UserResponse toDTO(User user){
        UserResponse dto = new UserResponse();
dto.setName(user.getUsername());
dto.setPassword(user.getPassword());
dto.setEmail(user.getEmail());
dto.setRole(user.getRole());
dto.setId(user.getId());

List<ItemResponse> itemDtos = user.getItems().stream().map(
    item -> {
        ItemResponse itemDto = new ItemResponse();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
       return itemDto;
    }).toList();

    dto.setItems(itemDtos);
        return dto;

    }

    private User fromDTO(UserRequest request){
        if (request == null) {return null; } 
     User user = new User();
     user.setUsername(request.getName());
     user.setEmail(request.getEmail());
     user.setPassword(request.getPassword());
     return user;
    
    }

     public User createUser(User user){
        return userRepository.save(user);
     }
   
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }
    

    public Optional<User> getUsersById(Long id){
        return userRepository.findById(id);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }

public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public String getUserPassword(String username) {
       
        User user = userRepository.findByUsername(username);
        return user != null ? user.getPassword() : null;
    }



    public void saveUser(com.lostfound.lostfound.model.User user) {
        userRepository.save(user);
    }



     public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public User updateUser(Long id, User updatedUser){
      return userRepository.findById(id)
        .map(user -> {
           user.setUsername(updatedUser.getUsername());
           user.setEmail(updatedUser.getEmail());
           user.setPassword(updatedUser.getPassword());
           return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}
