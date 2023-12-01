package com.example.kafkaconsumer.repositories;

import com.example.kafkaconsumer.entities.CountrySales;
import com.example.kafkaconsumer.entities.FlightEntity;
import org.springframework.data.repository.CrudRepository;

public interface SalesRepository  extends CrudRepository<CountrySales, Long> {
    CountrySales findByDestination(String destination);
}
