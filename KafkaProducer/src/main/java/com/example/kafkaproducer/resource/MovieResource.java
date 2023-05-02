package com.example.kafkaproducer.resource;

import com.example.kafkaproducer.model.MovieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieResource {

    @Autowired
    private KafkaTemplate<String, MovieRequest> movieKafkaTemplate;

    @Value("${MOVIE_TOPIC}")
    private String MOVIE_TOPIC;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public MovieRequest addMovie(@RequestBody MovieRequest request) {
        movieKafkaTemplate.send(MOVIE_TOPIC, request);
        return request;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String movies() {
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl
                = "http://consumer:8081/movies";

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);

        String productsJson = response.getBody();

        System.out.println(productsJson);
        return productsJson;
    }

    @RequestMapping(value = "/rating/{id}", method = RequestMethod.GET)
    public String movieRating(@PathVariable("id") final int id) {
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl
                = "http://consumer:8081/rating/" + id;

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);

        String productsJson = response.getBody();

        System.out.println(productsJson);
        return productsJson;
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String movieTop() {
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl
                = "http://consumer:8081/movies/top";

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);

        String productsJson = response.getBody();

        System.out.println(productsJson);
        return productsJson;
    }

    @RequestMapping(value = "/upvoteSortedReviews/{id}", method = RequestMethod.GET)
    public String movieUpvoteReviews(@PathVariable("id") final int id) {
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl
                = "http://consumer:8081/topReviews/" + id;

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);

        String productsJson = response.getBody();

        System.out.println(productsJson);
        return productsJson;
    }

    @RequestMapping(value = "/positiveSortedReviews/{id}", method = RequestMethod.GET)
    public String moviePositiveReviews(@PathVariable("id") final int id) {
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl
                = "http://consumer:8081/mostRatedReviews/" + id;

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);

        String productsJson = response.getBody();

        System.out.println(productsJson);
        return productsJson;
    }
}