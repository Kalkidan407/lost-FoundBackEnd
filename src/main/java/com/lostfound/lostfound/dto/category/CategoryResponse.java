package com.lostfound.lostfound.dto.category;

import java.util.List;

import com.lostfound.lostfound.dto.item.ItemResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
private List<ItemResponse> items;

    
}
