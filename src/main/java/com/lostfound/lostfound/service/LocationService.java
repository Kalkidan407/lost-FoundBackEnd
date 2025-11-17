package com.lostfound.lostfound.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.model.Location;
import com.lostfound.lostfound.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
    
   private final LocationRepository locationRepository;


  public Location addLocation(Location location){
     return locationRepository.save(location);
    }

   public List<Location> getAllLocations(){
    return locationRepository.findAll();
   }
    

    public Optional<Location>  getLocationById(Long id){
        return locationRepository.findById(id);
    }

    public void deleteLocationById(Long id){
        locationRepository.deleteById(id);
    }
    public void deleteAllLocations(){
        locationRepository.deleteAll();
    }

    public  Location updateLocation(Long id, Location updateedLocation){
        return locationRepository.findById(id)
            .map(item -> {
                item.setName(updateedLocation.getName());
                item.setLatitude(updateedLocation.getLatitude());
                item.setLongitude(updateedLocation.getLongitude());
                return locationRepository.save(item);
            }).orElseThrow(() -> new RuntimeException("Location not found with id:" + id));
    }



}
