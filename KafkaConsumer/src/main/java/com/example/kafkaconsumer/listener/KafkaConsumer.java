package com.example.kafkaconsumer.listener;

import com.example.kafkaconsumer.entities.MovieEntity;
import com.example.kafkaconsumer.entities.ReviewEntity;
import com.example.kafkaconsumer.model.MovieRequest;
import com.example.kafkaconsumer.model.ReviewRequest;
import com.example.kafkaconsumer.model.UpvoteRequest;
import com.example.kafkaconsumer.repositories.MovieRepository;
import com.example.kafkaconsumer.repositories.ReviewRepository;
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
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @KafkaListener(topics = {"${MOVIE_TOPIC}"}, groupId = "group_id")
    public void consumeMovie(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MovieRequest movie = objectMapper.readValue(message, MovieRequest.class);
        System.out.println("Consumed message: " + message);
        System.out.println(movie);
        MovieEntity m = new MovieEntity();
        m.setName(movie.getName());
        m.setDescription(movie.getDescription());
        m.setYear(movie.getYear());
        movieRepository.save(m);
        System.out.println(movieRepository.findAll());
    }

    @KafkaListener(topics = {"${REVIEW_TOPIC}"}, groupId = "group_id")
    public void consumeReview(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewRequest review = objectMapper.readValue(message, ReviewRequest.class);
        System.out.println("Consumed message: " + message);

        MovieEntity me = movieRepository.findById((long) review.getMovieId()).get();
        if(me == null) throw new IllegalArgumentException("Invalid ID");

        ReviewEntity r = new ReviewEntity();
        r.setName(review.getName());
        r.setText(review.getText());
        r.setRating(review.getRating());
        r.setUpvotes(0);
        r.setMovie(me);
        reviewRepository.save(r);
    }

    @KafkaListener(topics = {"${UPVOTE_TOPIC}"}, groupId = "group_id")
    public void consumeUpvote(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UpvoteRequest upvote = objectMapper.readValue(message, UpvoteRequest.class);
        System.out.println("Consumed message: " + message);

        ReviewEntity re = reviewRepository.findById((long) upvote.getReviewId()).get();
        if(re == null) throw new IllegalArgumentException("Invalid ID");

        re.setUpvotes(re.getUpvotes() + upvote.getUpvotesNum());
        reviewRepository.save(re);
    }
}