package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.model.Location;
import com.lostfound.lostfound.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/create")
    public Location createCategLocation(@RequestBody Location location){
        return locationService.addLocation(location);
    }

    @GetMapping
    public List<Location> getAllLocations(){
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id){
        return locationService.getLocationById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLocationById(@PathVariable Long id){
        locationService.deleteLocationById(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllLocations(){
        locationService.deleteAllLocations();
    }

    @PutMapping("/update/{id}")  
    public Location updateLocation(@PathVariable Long id, @RequestBody Location updatedLocation){
        return locationService.updateLocation(id, updatedLocation);
    }

}
