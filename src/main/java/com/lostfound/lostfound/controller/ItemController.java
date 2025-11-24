package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.item.ItemRequest;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor
@RequestMapping("/api/items")


public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<ItemResponse> addItem(@RequestBody ItemRequest dto) {
        ItemResponse created = itemService.addItem(dto);

        return ResponseEntity.ok(created);
    }

  
    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

  
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return ResponseEntity.ok("Item deleted successfully");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllItems() {
        itemService.deleteAllItems();
        return ResponseEntity.ok("All items deleted");
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long id,
            @RequestBody ItemRequest dto) {

        ItemResponse updated = itemService.updateItem(id, dto);
        return ResponseEntity.ok(updated);
    }
}
