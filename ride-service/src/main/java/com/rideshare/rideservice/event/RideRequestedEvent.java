package com.rideshare.rideservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event publish to kafka when ride requested
 * Matching service consumes this event
 * TOPIC: ride.requested
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestedEvent {

    private String rideId;

    private String riderId;

    private double pickupLongitude;

    private double pickupLatitude;

    private String pickupAddress;

    private double dropLongitude;

    private double dropLatitude;

    private String dropAddress;
}
