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

     public UserResponse createUser(UserRequest dto) {
        User user =  fromDTO(dto);
        User saved = userRepository.save(user);

        return toDTO(saved);
     }


   
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }
    

    public UserResponse getUsersById(Long id){
        User user = userRepository.findById(id)
                     .orElseThrow(() -> new RuntimeException("User not found with id:" + id));
        return toDTO(user);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }

public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
        return toDTO(user);
    }


    public UserResponse getUserPassword(String username) {
       
        User user = userRepository.findByUsername(username);
        return toDTO(user);
    }



    public void saveUser(com.lostfound.lostfound.model.User user) {
        userRepository.save(user);
    }



     public List<UserResponse> getAllUser(){
        List<User> user = userRepository.findAll();

        return user.stream().map(this::toDTO).toList();
    }


    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public UserResponse updateUser(Long id, UserRequest updatedUser){
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User name not found with" + id));
         
                user.setUsername(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());

                User save = userRepository.save(user);
              
      return toDTO(save);
    }

}
