package com.lostfound.lostfound.dto.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

@Data

@NoArgsConstructor
@AllArgsConstructor

public class ReportResponse {
    
    private Long id ;

    private String  message;
    private ItemResponse reportedItem;
    @JsonIgnore
    @Schema(hidden = true)
    private UserResponse reportedBy;
}
