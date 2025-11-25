package com.lostfound.lostfound.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.dto.category.CategoryRequest;
import com.lostfound.lostfound.dto.category.CategoryResponse;
import com.lostfound.lostfound.model.Category;
import com.lostfound.lostfound.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

private CategoryResponse toDTO(Category category){
  CategoryResponse dto = new CategoryResponse();
  dto.setName(category.getName());
  dto.setId(category.getId());

   List<ItemResponse> itemDtos = location.getItems().stream().map(
 item->{
    ItemResponse itemDto = new ItemResponse();
    itemDto.setId(item.getId());
    itemDto.setName(item.getName());
    itemDto.setDescription(item.getDescription());
    return itemDto;
 }).toList();

   dto.setItems(itemDtos);


  return dto;

}


private Category fromDTO(CategoryRequest request){
  if(request == null){
    return null;
  }

  Category category = new Category();
  category.setName(request.getName());
  return category;
}




    public List<CategoryResponse> getAllCategories() {
       List<Category> category = categoryRepository.findAll();
    return category.stream().map( this:: toDTO).toList();
        
    }

    public CategoryResponse addCategory(CategoryRequest dto) {
      Category category = fromDTO(dto);
      Category saved = categoryRepository.save(category);
        return toDTO(saved);
    }
    
    public void deleteCategoryById(Long id){
      categoryRepository.deleteById(id);
    }
    public void deleteAllCategories(){
      categoryRepository.deleteAll();
    }

    

 public Category updateCategory(Long id, Category updatedCategory) {
    return categoryRepository.findById(id)
        .map(category -> {
            category.setName(updatedCategory.getName());

            return categoryRepository.save(category);
        })
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
}


}