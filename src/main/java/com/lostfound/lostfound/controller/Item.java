package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.item.ItemRequest;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor
@RequestMapping("/api/items")


public class Item{

    private final ItemService itemService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ItemResponse> addItem(@RequestBody ItemRequest dto) {

        ItemResponse created = itemService.addItem(dto);
        return ResponseEntity.ok(created);
    }

  
    @GetMapping
       @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }


    @GetMapping("/{id}")
       @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

  
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return ResponseEntity.ok("Item deleted successfully with id " + id);
    }

    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteAllItems() {
        itemService.deleteAllItems();
        return ResponseEntity.ok("All items deleted");
    }


    @PutMapping("/update/{id}")
      @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long id,
            @RequestBody ItemRequest dto) {

        ItemResponse updated = itemService.updateItem(id, dto);
        return ResponseEntity.ok(updated);
    }
}
