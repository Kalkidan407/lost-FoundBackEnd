package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.dto.category.CategoryRequest;
import com.lostfound.lostfound.dto.category.CategoryResponse;

import com.lostfound.lostfound.service.CategoryService;

import lombok.RequiredArgsConstructor;

/**
 * CACHING & RATE LIMITING IMPLEMENTATION
 * 
 * CACHING:
 * - @Cacheable("categories") stores result in cache on first read
 * - Subsequent reads return from cache (very fast, no DB query)
 * - Cache stays valid until invalidated by create/update/delete
 * 
 * CACHE INVALIDATION:
 * - @CacheEvict clears cache when data changes
 * - Ensures clients get fresh data after updates
 * 
 * RATE LIMITING:
 * - Applied globally via RateLimitInterceptor
 * - Limit: 100 requests per minute per client
 * - Returns 429 error if exceeded
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryItemController {

    public final CategoryService categoryService;

    /**
     * Create new category
     * Clears cache because list of categories changed
     * 
     * @param category Category data to create
     * @return Created category response
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "categories", allEntries = true)  // Clear entire cache when new category added
    public CategoryResponse createCategory(@RequestBody CategoryRequest category){
        return categoryService.addCategory(category);
    }
    
    /**
     * Get all categories
     * First call: queries database, caches result
     * Subsequent calls: served from cache (10x faster!)
     * Cache cleared on create/update/delete
     * 
     * @return List of all categories
     */
    @GetMapping
    @Cacheable("categories")  // Store result in cache named "categories"
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    /**
     * Delete category by ID
     * Clears cache to ensure fresh data
     * 
     * @param id Category ID to delete
     * @return Success message
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "categories", allEntries = true)  // Clear cache when category deleted
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id){
      categoryService.deleteCategoryById(id);
      return ResponseEntity.ok("Category deleted successfully with id: " + id);
    }
    
    /**
     * Delete all categories
     * Clears cache completely
     * 
     * @return Success message
     */
    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "categories", allEntries = true)  // Clear entire cache
    public ResponseEntity<String> deleteAllCategories(){
        categoryService.deleteAllCategories();
        return ResponseEntity.ok("All categories deleted");
    }

    /**
     * Update existing category
     * Clears cache to ensure clients get updated data
     * 
     * @param id Category ID to update
     * @param updatedCategory New category data
     * @return Updated category response
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "categories", allEntries = true)  // Clear cache when category updated
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest updatedCategory){
        return categoryService.updateCategory(id, updatedCategory);
    }
    
}