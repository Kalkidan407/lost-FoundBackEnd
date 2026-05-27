package com.lostfound.lostfound.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

@AllArgsConstructor
public class LocationRequest {
 
    private String name;
    private Double longitude;
    private Double latitude;
    

    
}
