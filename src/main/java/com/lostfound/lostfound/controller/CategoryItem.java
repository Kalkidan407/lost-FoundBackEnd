package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.lostfound.lostfound.model.Category;
import com.lostfound.lostfound.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryItem {

    public final CategoryService categoryService;

    @PostMapping("/create")
     @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(  @RequestBody CategoryRequest category){
        return categoryService.addCategory(category);
    }
    
    @GetMapping
     @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
     
    @DeleteMapping("/delete/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id){
      categoryService.deleteCategoryById(id);
      return ResponseEntity.ok("Category deteted successfully with id" + id);
    }
    
  @DeleteMapping("/deleteAll")
   @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<String> deleteAllCategories(){
    categoryService.deleteAllCategories();
    return ResponseEntity.ok("All categories deleted");

  }


    @PutMapping("/update/{id}")
     @ResponseStatus(HttpStatus.OK)
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest updatedCategory){
      return categoryService.updateCategory(id, updatedCategory);
    }
    
}