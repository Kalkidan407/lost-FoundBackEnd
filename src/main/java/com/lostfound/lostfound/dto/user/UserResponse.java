package com.lostfound.lostfound.dto.user;

import java.util.List;

import javax.management.relation.Role;

import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.report.ReportResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    
    private Long id;
    private String name;
    private Role role;
    private String email;
    private List<ReportResponse> report;
    private List<ItemResponse> items;
    
}
