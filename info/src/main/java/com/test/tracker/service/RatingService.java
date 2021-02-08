package com.test.tracker.service;

import com.test.tracker.model.dto.RatingResponse;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    public RatingResponse getUserRating(Long userId){
        return RatingResponse.builder()
                .rating(4.6f)
                .build();
    }

}
