package com.lostfound.lostfound.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {

    private String  message;

  
     private Long reportedItemId;   
    private Long reportedById;
    private String reportType;
    
}
