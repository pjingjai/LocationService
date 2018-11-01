package com.example.LocationService.controller;

import com.example.LocationService.exception.ResourceNotFoundException;
import com.example.LocationService.model.Place;
import com.example.LocationService.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlaceController {

    @Autowired
    PlaceRepository placeRepository;
    
 // Get All Notes
    @GetMapping("/places")
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
    
 // Create a new Note
    @PostMapping("/places")
    public Place createPlace(@Valid @RequestBody Place place) {
        return placeRepository.save(place);
    }
    
 // Get a Single Note
    @GetMapping("/places/{id}")
    public Place getPlaceById(@PathVariable(value = "id") Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place", "id", placeId));
    }
    
 // Update a Note
    @PutMapping("/places/{id}")
    public Place updatePlace(@PathVariable(value = "id") Long placeId,
                                            @Valid @RequestBody Place placeDetails) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place", "id", placeId));

        place.setTitle(placeDetails.getTitle());
        place.setLat(placeDetails.getLat());
        place.setLng(placeDetails.getLng());
        place.setType(placeDetails.getType());
        place.setAddress(placeDetails.getAddress());
        place.setDescription(placeDetails.getDescription());

        Place updatedPlace = placeRepository.save(place);
        return updatedPlace;
    }
    
    // Delete a Note
    @DeleteMapping("/places/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable(value = "id") Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place", "id", placeId));

        placeRepository.delete(place);

        return ResponseEntity.ok().build();
    }
}