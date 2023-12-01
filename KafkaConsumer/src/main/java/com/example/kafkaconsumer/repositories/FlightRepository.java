package com.example.kafkaconsumer.repositories;

import com.example.kafkaconsumer.entities.FlightEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlightRepository  extends CrudRepository<FlightEntity, Long> {
    List<FlightEntity> findAll();
}
