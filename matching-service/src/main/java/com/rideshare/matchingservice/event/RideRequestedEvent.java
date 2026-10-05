package com.rideshare.matchingservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event consumed from Kafka topic: ride.requested
 * Published by Ride Service when a rider requests a ride
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestedEvent {

    private String riderId;

    private String rideId;

    private double pickupLongitude;

    private double pickupLatitude;

    private String pickupAddress;

    private double dropLongitude;

    private double dropLatitude;

    private String dropAddress;
}
