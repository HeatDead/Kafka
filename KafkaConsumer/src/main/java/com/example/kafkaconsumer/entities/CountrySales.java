package com.example.kafkaconsumer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class CountrySales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String destination;
    Long total_price;
    int count;
}
