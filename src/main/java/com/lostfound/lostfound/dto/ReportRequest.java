package com.lostfound.lostfound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {

    private String  message;

  
     private Long reportedItemId;   
    private Long reportedById;
    
}
