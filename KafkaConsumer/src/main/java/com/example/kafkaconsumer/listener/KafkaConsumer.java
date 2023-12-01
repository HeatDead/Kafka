package com.example.kafkaconsumer.listener;

import com.example.kafkaconsumer.entities.CountrySales;
import com.example.kafkaconsumer.entities.FlightEntity;
import com.example.kafkaconsumer.model.FlightRequest;
import com.example.kafkaconsumer.repositories.FlightRepository;
import com.example.kafkaconsumer.repositories.SalesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final FlightRepository flightRepository;
    private final SalesRepository salesRepository;

    @KafkaListener(topics = {"${SPARK_TOPIC}"}, groupId = "group_id")
    public void consumeMovie(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FlightRequest flight = objectMapper.readValue(message, FlightRequest.class);
        System.out.println("Consumed message: " + message);
        System.out.println(flight);
        FlightEntity fe = new FlightEntity();
        fe.setDestination(flight.getDestination());
        fe.setPrice(flight.getPrice());
        fe.setDate(flight.getDate());
        flightRepository.save(fe);

        CountrySales s = salesRepository.findByDestination(flight.getDestination());
        if(s == null) {
            s = new CountrySales();
            s.setDestination(flight.getDestination());
            s.setTotal_price(fe.getPrice());
            s.setCount(1);
        }else {
            s.setTotal_price(s.getTotal_price() + fe.getPrice());
            s.setCount(s.getCount() + 1);
        }

        salesRepository.save(s);
        //System.out.println(flight.findAll());
    }
}