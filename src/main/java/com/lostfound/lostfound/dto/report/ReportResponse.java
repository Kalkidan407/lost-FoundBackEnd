package com.lostfound.lostfound.dto.report;

import com.lostfound.lostfound.dto.item.ItemResponse;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReportResponse {
    
    private Long id;
    private String message;
    private ItemResponse reportedItem;
    
    
}
