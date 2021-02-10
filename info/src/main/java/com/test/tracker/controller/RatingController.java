package com.test.tracker.controller;

import com.test.tracker.model.dto.RatingResponse;
import com.test.tracker.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "user/{userId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingResponse getRating(@PathVariable("userId") Long userId) {
        return ratingService.getUserRating(userId);
    }

    @PostMapping(value = "user/rating", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Float> getRating(@RequestBody List<Long> users) {
        return ratingService.getRatingForUserList(users);
    }

}
