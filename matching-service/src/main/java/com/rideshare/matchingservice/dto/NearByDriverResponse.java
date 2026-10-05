package com.rideshare.matchingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response received from Location service
 * When querying for nearby drivers
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearByDriverResponse {

    private String driverId;

    private double longitude;

    private double latitude;

    private double distanceInKm;
}
