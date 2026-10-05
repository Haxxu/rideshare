package com.rideshare.locationservice.controller;

import com.rideshare.locationservice.dto.DriverLocationRequest;
import com.rideshare.locationservice.dto.NearByDriverResponse;
import com.rideshare.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    // driver phone call this every 3 second
    @PostMapping("/drivers/update")
    public ResponseEntity<String> updateDriverLocation(
            @RequestBody DriverLocationRequest request
    ) {
        locationService.updateDriverLocation(request);

        return ResponseEntity.ok("Driver location updated.");
    }

    // Matching service calls this when ride is requested
    @GetMapping("/drivers/nearby")
    public ResponseEntity<List<NearByDriverResponse>> getNearByDrivers(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam (defaultValue = "5.0") double radius
    ) {
        return ResponseEntity.ok(locationService.findNearByDrivers(longitude, latitude, radius));
    }

    // Called when driver goes offline
    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<String> removeDriver(
            @PathVariable String driverId
    ) {
        locationService.removeDriver(driverId);

        return ResponseEntity.ok("Driver removed successfully");
    }
}
