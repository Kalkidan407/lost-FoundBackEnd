package com.lostfound.lostfound.dto.report;

import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.user.UserResponse;

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
