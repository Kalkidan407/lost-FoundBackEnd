package com.lostfound.lostfound.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.location.LocationRequest;
import com.lostfound.lostfound.dto.location.LocationResponse;
import com.lostfound.lostfound.model.Location;
import com.lostfound.lostfound.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
    
   private final LocationRepository locationRepository;
 

private LocationResponse toDTO( Location location){
    LocationResponse dto = new LocationResponse(); 
    dto.setId(location.getId());
    dto.setLatitude(location.getLatitude());
    dto.setLongitude(location.getLongitude());

List<ItemResponse> itemDtos = location.getItems()== null ? List.of(): location.getItems().stream().map(
    item -> {
        ItemResponse itemDto = new ItemResponse();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
       return itemDto;
    }).toList();

   dto.setItems(itemDtos);
    
    return dto;
}


private Location fromDTO(LocationRequest request){
    if (request == null) {
    return null;
}

    Location location = new Location();
    location.setName(request.getName());
location.setLatitude(request.getLatitude());
location.setLongitude(request.getLongitude());


    return location;
}


// service  methods //

  public LocationResponse addLocation(LocationRequest dto) {

    Location location = fromDTO(dto); 
    Location saved = locationRepository.save(location);

    return toDTO(saved); 
    
    }

   public List<LocationResponse> getAllLocations(){
   
    // fetch entity from DB 
    List<Location> location = locationRepository.findAll();
    return location.stream().map( this:: toDTO)//convert each Entity to Response to DTO
    .toList();
   }

    
    public LocationResponse  getLocationById(Long id){
        Location location = locationRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        return toDTO(location);
    }

    public void deleteLocationById(Long id){
        locationRepository.deleteById(id);
    }
    public void deleteAllLocations(){
        locationRepository.deleteAll();
    }


 public  LocationResponse updateLocation(Long id, LocationRequest updateedLocation){
       
Location location = locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Location entity not found")); //fetch the existing locationEntity from db

  location.setName(updateedLocation.getName());  //update fields 
  location.setLatitude(updateedLocation.getLatitude());
 location.setLongitude(updateedLocation.getLongitude());
 
 Location save = locationRepository.save(location);  //save the updated entity

        return toDTO(save); //convert saved entity to DTO response , so use toDTO()
        
    }



}
