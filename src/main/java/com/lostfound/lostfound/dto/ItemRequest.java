package com.lostfound.lostfound.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    @NotBlank(message = "name is required")           
    private String name;

    @Size(max = 2000, message = "description too long")
    private String description;

    private String photoUrl;

    private Long userId;
    private Long categoryId;
    private Long locationId;
   
}
