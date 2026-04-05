package com.lostfound.lostfound.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LocationRequest {
 
    private String  name;
    private String longitude;
    private String latitude;
    

    
}
