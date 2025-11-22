package com.lostfound.lostfound.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    private boolean status;
    private Long userId;
    private Long categoryId;
    private Long locationId;
}
