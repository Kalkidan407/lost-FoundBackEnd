package com.lostfound.lostfound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReportResponse {
    
    private Long id ;

    private String  message;
    private ItemResponse reportedItem;
    private UserResponse reportedBy;
}
