package com.rideshare.rideservice.dto;

import com.rideshare.rideservice.model.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {

    private String id;

    private String riderId;

    private String driverId;

    private double pickupLongitude;

    private double pickupLatitude;

    private String pickupAddress;

    private double dropLongitude;

    private double dropLatitude;

    private String dropAddress;

    // Ride status - tracks the lifecycle
    private RideStatus status;

    private double estimatedFare;

    private double actualFare;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}
