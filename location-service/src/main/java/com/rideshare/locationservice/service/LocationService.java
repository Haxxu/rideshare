package com.rideshare.locationservice.service;

import com.rideshare.locationservice.dto.DriverLocationRequest;
import com.rideshare.locationservice.dto.NearByDriverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final RedisTemplate<String, String> redisTemplate;

    // Redis key for all driver locations
    private static final String DRIVERS_GEO_KEY = "drivers:locations";

    /**
     * Update driver location in Redis.
     * Called every 3 seconds by driver's phone.
     * Maps to Redis GEOADD command.
     */

    public void updateDriverLocation(DriverLocationRequest request) {
        log.info("Updating location for driver: {}", request.getDriverId());

        // IMPORTANT: longitude FIRST, latitude SECOND - GeoSpatial Standard
        Point driverPoint = new Point(
                request.getLongitude(),
                request.getLatitude()
        );

        redisTemplate.opsForGeo().add(DRIVERS_GEO_KEY, driverPoint, request.getDriverId());

        log.info("Location updated for driver: {}", request.getDriverId());
    }

    /**
     * Find nearby drivers within given radius.
     * Called by Matching Service on ride request.
     * Maps to Redis GEORADIUS command.
     */

    public List<NearByDriverResponse> findNearByDrivers(
            double longitude,
            double latitude,
            double radiusInKm
    ) {
        log.info("Finding drivers near long: {}, lat: {} within {}Km", longitude, latitude, radiusInKm);

        Circle searchArea = new Circle(
                new Point(longitude, latitude),
                new Distance(radiusInKm, Metrics.KILOMETERS)
        );

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo().radius(
                    DRIVERS_GEO_KEY,
                    searchArea,
                    RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                            .includeCoordinates()
                            .includeDistance()
                            .sortAscending()
                            .limit(10)
                );

        List<NearByDriverResponse> nearByDrivers = new ArrayList<>();

        if (results != null) {
            results.getContent().forEach(result -> {
                RedisGeoCommands.GeoLocation<String> location = result.getContent();
                nearByDrivers.add(new NearByDriverResponse(
                        location.getName(),
                        location.getPoint().getY(),
                        location.getPoint().getX(),
                        result.getDistance().getValue()
                ));
            });
        }

        log.info("Found {} drivers nearby", nearByDrivers.size() );

        return nearByDrivers;
    }

    /**
     * Remove driver when they go offline
     * Maps to Redis ZREM command
     */

    public void removeDriver(String driverId) {
        log.info("Removing driver: {}", driverId);

        redisTemplate.opsForGeo().remove(DRIVERS_GEO_KEY, driverId);
    }
}
