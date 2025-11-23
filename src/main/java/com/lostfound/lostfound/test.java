



// package com.lostfound.lostfound.service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;
// import com.lostfound.lostfound.model.Item;
// import com.lostfound.lostfound.repository.ItemRepository;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class ItemService {

//     private final ItemRepository itemRepository;
//     private final UserRepository userRepository;
//      private final CategoryRepository categoryRepo;
//     private final LocationRepository locationRepo;


//     public List<Item> getAllItems() {
//         return itemRepository.findAll();
//     }


//     public Item addItem(Item item) {
//         return itemRepository.save(item);
//     }

//     public Optional<Item> getItemById(Long id) {
//     return itemRepository.findById(id);
// }


//     public void deleteItemById(Long id){
//       itemRepository.deleteById(id);
//     }
//     public void deleteAllItems(){
//       itemRepository.deleteAll();
//     }

//     public Item updateItem(Long id, Item updatedItem){
//       return itemRepository.findById(id)
//         .map(item -> {
//            item.setName(updatedItem.getName());
//            item.setDescription(updatedItem.getDescription());
//            item.setLocationFound(updatedItem.getLocationFound());
//            item.setPhotoUrl(updatedItem.getPhotoUrl());
//             item.setStatus(updatedItem.isStatus());
//     return itemRepository.save(item);

//         }).orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
//     }


// }

