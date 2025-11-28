package com.lostfound.lostfound.dto.location;

import java.util.List;

import com.lostfound.lostfound.dto.item.ItemResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long id;
    private String  name;
    private String longitude;
    private String latitude;
    private List<ItemResponse> items;

    
}
