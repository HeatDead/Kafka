package com.example.kafkaproducer;

import com.example.kafkaproducer.model.FlightRequest;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class FlightGenerator {
    private final Faker faker;

    public FlightGenerator() {
        this.faker = new Faker();
    }

    public FlightRequest generateFlight() {
        FlightRequest flightRequest = new FlightRequest();
        //flightRequest.setFlightId(UUID.randomUUID());
        flightRequest.setDestination(faker.country().name());
        flightRequest.setPrice(faker.number().randomNumber(4, false));
        flightRequest.setDate(new Date());

        return flightRequest;
    }
}