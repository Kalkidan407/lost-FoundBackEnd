package com.lostfound.lostfound.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationRequest {
 
    private String  name;
    private String longitude;
    private String latitude;
    

    
}
