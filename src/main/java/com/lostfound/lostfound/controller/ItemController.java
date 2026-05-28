package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

 import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lostfound.lostfound.dto.item.ItemRequest;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.service.ItemService;
import lombok.RequiredArgsConstructor;

/**
 * ITEM CONTROLLER WITH CACHING & RATE LIMITING
 * 
 * CACHE STRATEGY:
 * - "items": All items list
 * - "item::{id}": Individual item details
 * 
 * NOTE: Paginated results are cached by keyword and pagination params
 * Cache key format: "items::{keyword}::{page}::{size}"
 * 
 * RATE LIMITING:
 * Applied globally via interceptor
 * Limit: 100 requests per minute per IP/client
 */
@RestController 
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController{

    private final ItemService itemService;

    /**
     * Create new item
     * Clears item cache when new item is added
     * 
     * @param dto Item creation data
     * @return Created item response
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "items", allEntries = true)  // Clear cache when new item added
    public ResponseEntity<ItemResponse> addItem(@RequestBody ItemRequest dto) {
        ItemResponse created = itemService.addItem(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Get all items with pagination and search
     * Cache key includes: keyword, page, size
     * Example: items::electronics::0::5 (electronics on page 0, 5 items per page)
     * 
     * NOTE: Caching pagination might return stale data for fast-changing content
     * Consider shorter TTL or bypassing cache for real-time data
     * 
     * @param keyword Optional search keyword
     * @param page Page number (0-indexed)
     * @param size Items per page
     * @return Paginated items
     */
    @GetMapping
    @Cacheable(
        value = "items",
        key = "'all-items-' + #keyword + '-' + #page + '-' + #size"  // Cache key includes search params
    )
    public Page<ItemResponse> getAllItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return itemService.getAllItems(
                keyword,
                page,
                size
        );
    }

    /**
     * Get individual item by ID
     * Each item cached separately
     * Cache key: "item::{id}"
     * 
     * @param id Item ID
     * @return Item details
     */
    @GetMapping("/{id}")
    @Cacheable(value = "item", key = "#id")  // Cache with ID as key
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    /**
     * Delete item by ID
     * Clears both all-items cache and specific item cache
     * 
     * @param id Item ID to delete
     * @return Success message
     */
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"items", "item"}, allEntries = true)  // Clear all item caches
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return ResponseEntity.ok("Item deleted successfully with id " + id);
    }

    /**
     * Delete all items
     * Clears entire item cache
     * 
     * @return Success message
     */
    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"items", "item"}, allEntries = true)  // Clear all caches
    public ResponseEntity<String> deleteAllItems() {
        itemService.deleteAllItems();
        return ResponseEntity.ok("All items deleted");
    }

    /**
     * Update item
     * Invalidates caches to ensure clients get updated data
     * 
     * @param id Item ID to update
     * @param dto Updated item data
     * @return Updated item response
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = {"items", "item"}, allEntries = true)  // Clear all caches on update
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long id,
            @RequestBody ItemRequest dto) {

        ItemResponse updated = itemService.updateItem(id, dto);
        return ResponseEntity.ok(updated);
    }
}







