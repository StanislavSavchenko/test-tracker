package com.test.tracker.controller;

import com.test.tracker.model.dto.RatingResponse;
import com.test.tracker.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "user/{userId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingResponse buildReport(@PathVariable("userId") Long userId) {
        return ratingService.getUserRating(userId);
    }

}
