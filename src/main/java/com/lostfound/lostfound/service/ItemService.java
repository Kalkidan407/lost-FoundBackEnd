package com.lostfound.lostfound.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lostfound.lostfound.dto.item.ItemRequest;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.model.Item;
import com.lostfound.lostfound.model.User;
import com.lostfound.lostfound.model.Location;
import com.lostfound.lostfound.model.Category;
import com.lostfound.lostfound.repository.UserRepository;
import com.lostfound.lostfound.repository.CategoryRepository;
import com.lostfound.lostfound.repository.LocationRepository;
import com.lostfound.lostfound.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepo;
    private final LocationRepository locationRepo;

    private User getCurrentUser() {
      
    Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();

    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }


    // --------------------------
    // Convert Entity -> Response DTO, /get/
    // --------------------------

private ItemResponse toDTO(Item item) {
    ItemResponse dto = new ItemResponse();
  
    dto.setName(item.getName());
    dto.setDescription(item.getDescription());
    dto.setPhotoUrl(item.getPhotoUrl());
    dto.setStatus(item.isStatus());
    if (item.getUser() != null) {
        dto.setUserId(item.getUser().getId());
        dto.setUserName(item.getUser().getUsername());
    }
    if (item.getCategory() != null) {
        dto.setCategoryId(item.getCategory().getId());
        dto.setCategoryName(item.getCategory().getName());
    }
    if (item.getLocation() != null) {
        dto.setLocationId(item.getLocation().getId());
        dto.setLocationName(item.getLocation().getName());
    }
    // report mapping later (when implemented)
    return dto;
}


    // --------------------------
    // Convert Request DTO -> Entity (Create/update, Incoming Data → Backend)
    // --------------------------

    private Item fromDTO(ItemRequest dto) {

        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPhotoUrl(dto.getPhotoUrl());
        item.setStatus(false); 

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        item.setUser(user);

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        item.setCategory(category);

       
        Location location = locationRepo.findById(dto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));
        item.setLocation(location);

        return item;

    }


    // -------------------------- //
    //     Service Methods
    // -------------------------- //

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ItemResponse addItem(ItemRequest dto) {
      User currentUser = getCurrentUser();

        Item item = fromDTO(dto);
         item.setUser(currentUser);
        Item savedItem = itemRepository.save(item);
        return toDTO(savedItem);
    }

    public  ItemResponse getItemById(Long id) {
        return itemRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

  

    public void deleteItemById(Long id){

    Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

    User currentUser = getCurrentUser();

    boolean isAdmin = currentUser.getRole().name().equals("ADMIN");

    boolean isOwner = item.getUser().getId().equals(currentUser.getId());

    if(!isAdmin && !isOwner){
        throw new RuntimeException("You are not allowed to delete this item");
    }

    itemRepository.delete(item);
}


   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAllItems(){
        itemRepository.deleteAll();
    }

    public ItemResponse updateItem(Long id, ItemRequest dto){

Item item = itemRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Item not found"));

   User currentUser = getCurrentUser();  
   boolean isAdmin = currentUser.getRole().name().equals("ADMIN");    
   boolean isOwner = item.getUser().getId().equals(currentUser.getId());
   
   if(!isAdmin && !isOwner){
    throw new RuntimeException("Not allowed to update this item");
   }

   item.setName(dto.getName());
   item.setDescription(dto.getDescription());
   item.setPhotoUrl(dto.getPhotoUrl());
   Item updated = itemRepository.save(item);

        return toDTO(updated);
}

}
