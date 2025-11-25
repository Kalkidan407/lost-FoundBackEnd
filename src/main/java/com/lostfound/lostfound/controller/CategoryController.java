package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.model.Category;
import com.lostfound.lostfound.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    public final CategoryService categoryService;

    @PostMapping("/create")
     @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    
    @GetMapping
     @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
     
    @DeleteMapping("/delete/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id){
      categoryService.deleteCategoryById(id);
    }
    
  @DeleteMapping("/deleteAll")
   @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAllCategories(){
    categoryService.deleteAllCategories();

  }


    @PutMapping("/update/{id}")
     @ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory){
      return categoryService.updateCategory(id, updatedCategory);
    }
    
}