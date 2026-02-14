package com.lostfound.lostfound.dto.item;
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
    private String userName;

    private Long categoryId;
    private String categoryName;

    private Long locationId;
    private String locationName;

    private Long reportId;
    private String reportedBy;
}


