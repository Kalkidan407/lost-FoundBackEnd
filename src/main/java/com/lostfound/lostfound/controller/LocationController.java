package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

/**
 * LOCATION CONTROLLER WITH CACHING
 * 
 * CACHE STRATEGY:
 * - "locations": All locations (rarely changes)
 * - "location::{id}": Individual location (user-specific caching)
 * 
 * RATE LIMITING:
 * Applied globally - max 100 requests/minute per client
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    /**
     * Create new location
     * Invalidates all cached locations since list changed
     * 
     * @param request Location creation data
     * @return Created location response
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "locations", allEntries = true)  // Clear cache on new location
    public LocationResponse createCategLocation(@Valid @RequestBody LocationRequest request){
        return locationService.addLocation(request);
    }

    /**
     * Get all locations
     * Cached for performance (locations rarely change)
     * Served from cache on subsequent requests (~10x faster)
     * 
     * @return List of all locations
     */
    @GetMapping
    @Cacheable("locations")  // Cache all locations
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<LocationResponse> getAllLocations() {
        return locationService.getAllLocations();
    }

    /**
     * Get location by ID
     * Each location cached separately with ID as cache key
     * Example: cache key = "location::5" for location with ID 5
     * 
     * @param id Location ID
     * @return Location details
     */
    @GetMapping("/{id}")
    @Cacheable(value = "location", key = "#id")  // Cache with ID as key: location::{id}
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public LocationResponse getLocationById(@PathVariable Long id){
        return locationService.getLocationById(id);
    }

    /**
     * Delete location by ID
     * Invalidates both all-locations cache and specific location cache
     * 
     * @param id Location ID to delete
     * @return Success message
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = {"locations", "location"}, allEntries = true)  // Clear both caches
    public ResponseEntity<String> deleteLocationById(@PathVariable Long id){
        locationService.deleteLocationById(id);
        return ResponseEntity.ok("Location deleted successfully with id: " + id);
    }

    /**
     * Delete all locations
     * Clears all caches
     * 
     * @return Success message
     */
    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = {"locations", "location"}, allEntries = true)  // Clear all caches
    public ResponseEntity<String> deleteAllLocations(){
        locationService.deleteAllLocations();
        return ResponseEntity.ok("All locations deleted");
    }

    /**
     * Update location
     * Invalidates caches to ensure fresh data
     * 
     * @param id Location ID
     * @param request Updated location data
     * @return Updated location response
     */
    @PutMapping("/update/{id}")  
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = {"locations", "location"}, allEntries = true)  // Clear both caches on update
    public LocationResponse updateLocation(@PathVariable Long id, @Valid @RequestBody LocationRequest request){
        return locationService.updateLocation(id, request);
    }

}

