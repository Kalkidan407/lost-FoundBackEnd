package com.lostfound.lostfound.dto.location;

import java.util.List;

import com.lostfound.lostfound.dto.item.ItemResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
    private String address;
    private String description;
    private String timezone;
    private boolean verified;
    private List<ItemResponse> items;

    
}
