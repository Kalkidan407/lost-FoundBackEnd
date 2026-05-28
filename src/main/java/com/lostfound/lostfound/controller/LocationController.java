package com.lostfound.lostfound.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.lostfound.lostfound.dto.location.LocationRequest;
import com.lostfound.lostfound.dto.location.LocationResponse;
import com.lostfound.lostfound.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

  
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")  
    public LocationResponse createCategLocation(@Valid @RequestBody LocationRequest request){
        return locationService.addLocation(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<LocationResponse> getAllLocations() {
        return locationService.getAllLocations();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public LocationResponse getLocationById(@PathVariable Long id){
        return locationService.getLocationById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteLocationById(@PathVariable Long id){
        locationService.deleteLocationById(id);
        return ResponseEntity.ok("Location deleted successfully with id: " + id);
    }

    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAllLocations(){
        locationService.deleteAllLocations();
        return ResponseEntity.ok("All locations deleted");
    }


    @PutMapping("/update/{id}")  
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public LocationResponse updateLocation(@PathVariable Long id, @Valid @RequestBody LocationRequest request){
        return locationService.updateLocation(id, request);
    }

}

