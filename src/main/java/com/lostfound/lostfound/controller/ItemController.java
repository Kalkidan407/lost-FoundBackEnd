package com.lostfound.lostfound.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.lostfound.lostfound.model.Item;
import com.lostfound.lostfound.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @GetMapping("/")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

   @GetMapping("/{id}")
  public Item getItemById(@PathVariable Long id){
    return itemService.getItemById(id)
         .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
  }

  @DeleteMapping("/{id}")
  public void deleteItemById(@PathVariable Long id){
    itemService.deleteItemById(id);
  }
  @DeleteMapping
  public void deleteAllItems(){
    itemService.deleteAllItems();
  }

   
}
