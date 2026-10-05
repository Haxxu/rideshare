package com.rideshare.matchingservice.service;

import com.rideshare.matchingservice.event.RideRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideEventConsumer {

    private final MatchingService matchingService;

    /**
     * Listen to ride.requested Kafka topic.
     * Triggered every time Ride service published a new ride request
     *
     * FLOW:
     * Rider Service -> Kafka (ride.requested) -> This consumer -> Matching Service
     */

    @KafkaListener(
            topics = "ride.requested",
            groupId = "matchin-service-group"
    )
    public void consumeRideRequestedEvent(RideRequestedEvent event) {
        try {
            matchingService.matchDriverForRide(event);
        } catch (Exception e) {
            log.error("Error processing ride request: {} - {}",
                    event.getRideId(), e.getMessage());
            // In production: send to dead letter queue for retry
        }
    }
}
