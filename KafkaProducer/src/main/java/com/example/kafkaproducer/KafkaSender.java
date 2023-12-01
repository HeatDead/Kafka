package com.example.kafkaproducer;

import com.example.kafkaproducer.model.FlightRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KafkaSender {
    @Value("${SPARK_TOPIC}")
    private String SPARK_TOPIC;

    private final KafkaTemplate<String, FlightRequest> kafkaFlightTemplate;

    private final FlightGenerator flightGenerator;

    Random rand;

    public KafkaSender(KafkaTemplate<String, FlightRequest> kafkaFlightTemplate, FlightGenerator flightGenerator) {
        this.kafkaFlightTemplate = kafkaFlightTemplate;
        this.flightGenerator = flightGenerator;
        rand = new Random();
    }

    @Scheduled(fixedRate = 1000)
    public void sender() {
        FlightRequest flightRequest = flightGenerator.generateFlight();
        kafkaFlightTemplate.send(SPARK_TOPIC, flightRequest);
        System.out.println("{destination:\"" + flightRequest.getDestination() + "\",total_price:" + flightRequest.getPrice() + ",count:" + rand.nextInt(1, 8) + "}");
    }
}