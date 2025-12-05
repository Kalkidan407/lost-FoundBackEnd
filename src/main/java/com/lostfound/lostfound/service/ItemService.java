package com.lostfound.lostfound.service;

import java.util.List;
import java.util.stream.Collectors;
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

    // --------------------------
    // Convert Entity -> Response DTO, /get/
    // --------------------------

   private ItemResponse toDTO(Item item) {
    ItemResponse dto = new ItemResponse();

    dto.setId(item.getId());
    dto.setName(item.getName());
    dto.setDescription(item.getDescription());
    dto.setPhotoUrl(item.getPhotoUrl());
    dto.setStatus(item.isStatus());
    

    // --- SAFE NULL CHECKS ---

    dto.setReportedBy(
        item.getUser() != null ? item.getUser().getUsername() : null
    );

    dto.setCategoryName(
        item.getCategory() != null ? item.getCategory().getName() : null
    );

    dto.setLocationName(
        item.getLocation() != null ? item.getLocation().getName() : null
    );


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


    // --------------------------
    // Service Methods
    // --------------------------

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ItemResponse addItem(ItemRequest dto) {
        Item item = fromDTO(dto);
        Item savedItem = itemRepository.save(item);
        return toDTO(savedItem);
    }

    public  ItemResponse getItemById(Long id) {
        return itemRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void deleteItemById(Long id){
        itemRepository.deleteById(id);
    }

    public void deleteAllItems(){
        itemRepository.deleteAll();
    }

    public ItemResponse updateItem(Long id, ItemRequest dto){
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(dto.getName());
                    item.setDescription(dto.getDescription());
                    item.setPhotoUrl(dto.getPhotoUrl());

                    // update relationships
                    item.setUser(userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found")));

                    item.setCategory(categoryRepo.findById(dto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found")));

                    item.setLocation(locationRepo.findById(dto.getLocationId())
                        .orElseThrow(() -> new RuntimeException("Location not found")));

                    Item updated = itemRepository.save(item);
                    return toDTO(updated);
                }).orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
