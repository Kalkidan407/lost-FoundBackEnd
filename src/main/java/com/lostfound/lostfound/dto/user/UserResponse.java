package com.lostfound.lostfound.dto.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.report.ReportResponse;
import com.lostfound.lostfound.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {
    
    private Long id;
    private String name;
    private Role role;
    private String email;
    @JsonIgnore
    @Schema(hidden = true)
    private List<ReportResponse> report;
    @JsonIgnore
    @Schema(hidden = true)
    private List<ItemResponse> items;
    
}
