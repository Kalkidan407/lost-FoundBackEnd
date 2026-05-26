package com.lostfound.lostfound.dto.item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    
    @NotBlank(message = "name is required")           
    private String name;

    @Size(max = 2000, message = "description too long")
    private String description;
    
    @Size(min = 2, message = "at least 2 photos are required")
    @Size(max = 10, message = "maximum 10 photos allowed")
    private List<String> photos;
    
    private Long userId;
    private Long categoryId;
    private Long locationId;

   
}
