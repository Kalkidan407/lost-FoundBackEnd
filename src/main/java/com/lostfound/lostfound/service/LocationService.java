package com.lostfound.lostfound.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.lostfound.lostfound.dto.item.ItemResponse;
import com.lostfound.lostfound.dto.location.LocationRequest;
import com.lostfound.lostfound.dto.location.LocationResponse;
import com.lostfound.lostfound.model.Location;
import com.lostfound.lostfound.repository.LocationRepository;
import com.lostfound.lostfound.exception.ResourceNotFoundException;
import com.lostfound.lostfound.exception.InvalidLocationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
    
   private final LocationRepository locationRepository;

   private void validateLocationCoordinates( Double latitude, Double longitude, String name) {
       if (latitude == null || longitude == null) {
           throw new InvalidLocationException("Latitude and longitude are required");
       }
       if (latitude < -90 || latitude > 90) {
           throw new InvalidLocationException("Invalid latitude. Must be between -90 and 90");
       }
       if (longitude < -180 || longitude > 180) {
           throw new InvalidLocationException("Invalid longitude. Must be between -180 and 180");
       }
       if (name == null || name.trim().isEmpty()) {
           throw new InvalidLocationException("Location name is required and cannot be empty");
       }
   }

   private LocationResponse toDTO(Location location) {
       LocationResponse dto = new LocationResponse(); 
       dto.setId(location.getId());
       dto.setName(location.getName());
       dto.setLatitude(location.getLatitude());
       dto.setLongitude(location.getLongitude());
       dto.setAddress(location.getAddress());
       dto.setDescription(location.getDescription());
       dto.setTimezone(location.getTimezone());
       dto.setVerified(location.isVerified());

       List<ItemResponse> itemDtos = location.getItems() == null ? List.of() : location.getItems().stream().map(
           item -> {
               ItemResponse itemDto = new ItemResponse();
               itemDto.setId(item.getId());
               itemDto.setName(item.getName());
              return itemDto;
           }).toList();

      dto.setItems(itemDtos);
       
       return dto;
   }

   private Location fromDTO(LocationRequest request) {
       
       if (request == null) {
           throw new InvalidLocationException("Location request cannot be null");
       }

       validateLocationCoordinates(request.getLatitude(), request.getLongitude(), request.getName());

       Location location = new Location();
       location.setName(request.getName().trim());
       location.setLatitude(request.getLatitude());
       location.setLongitude(request.getLongitude());
       location.setAddress(request.getAddress());
       location.setDescription(request.getDescription());
       location.setTimezone(request.getTimezone());
       location.setVerified(false);

       return location;
   }

    @CacheEvict(value = "locations", allEntries = true)
   public LocationResponse addLocation(LocationRequest dto) {
       Location location = fromDTO(dto); 
       Location saved = locationRepository.save(location);
       return toDTO(saved); 
   }

     @Cacheable("locations")  
   public List<LocationResponse> getAllLocations() {
       List<Location> locations = locationRepository.findAll();
       return locations.stream().map(this::toDTO).toList();
   }

     @Cacheable(value = "location", key = "#id")  
   public LocationResponse getLocationById(Long id) {
       if (id == null || id <= 0) {
           throw new InvalidLocationException("Invalid location ID");
       }
       Location location = locationRepository.findById(id)
                           .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
       return toDTO(location);
   }


   @CacheEvict(value = {"locations", "location"}, allEntries = true)  
   public void deleteLocationById(Long id) {
       if (id == null || id <= 0) {
           throw new InvalidLocationException("Invalid location ID");
       }
       Location location = locationRepository.findById(id)
                           .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
       location.setDeleted(true);
       location.setDeletedAt(java.time.LocalDateTime.now());
       locationRepository.save(location);
   }

    @CacheEvict(value = {"locations", "location"}, allEntries = true)  
   public void deleteAllLocations() {
       locationRepository.findAll().forEach(location -> {
           location.setDeleted(true);
           location.setDeletedAt(java.time.LocalDateTime.now());
           locationRepository.save(location);
       });
   }


 @CacheEvict(value = {"locations", "location"}, allEntries = true)  
   public LocationResponse updateLocation(Long id, LocationRequest updatedLocation) {

       if (id == null || id <= 0) {
           throw new InvalidLocationException("Invalid location ID");
       }
       
       Location location = locationRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));

       validateLocationCoordinates(updatedLocation.getLatitude(), updatedLocation.getLongitude(), updatedLocation.getName());
       
       location.setName(updatedLocation.getName().trim());
       location.setLatitude(updatedLocation.getLatitude());
       location.setLongitude(updatedLocation.getLongitude());
       location.setAddress(updatedLocation.getAddress());
       location.setDescription(updatedLocation.getDescription());
       location.setTimezone(updatedLocation.getTimezone());
    
       Location save = locationRepository.save(location);

       return toDTO(save);
   }


}
