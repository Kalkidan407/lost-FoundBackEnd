package com.lostfound.lostfound.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank
    private String name;

    
}
