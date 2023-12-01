package com.example.kafkaconsumer.model;

import lombok.Data;

import java.util.Date;

@Data
public class FlightRequest {
    String destination;
    Long price;
    Date date;
}
