package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.dto.location.LocationRequest;
import com.lostfound.lostfound.dto.location.LocationResponse;
import com.lostfound.lostfound.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponse createCategLocation(@RequestBody LocationRequest request){
        return locationService.addLocation(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocationResponse> getAllLocations(){
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationResponse getLocationById(@PathVariable Long id){
        return locationService.getLocationById(id);
               
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationById(@PathVariable Long id){
        locationService.deleteLocationById(id);
    }

    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllLocations(){
        locationService.deleteAllLocations();
    }

    @PutMapping("/update/{id}")  
    @ResponseStatus(HttpStatus.OK)
    public LocationResponse updateLocation(@PathVariable Long id, @RequestBody LocationRequest request){
        return locationService.updateLocation(id, request);
    }

}
