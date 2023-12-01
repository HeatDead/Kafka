package com.example.kafkaproducer.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FlightRequest {
    String destination;
    Long price;
    Date date;
}
