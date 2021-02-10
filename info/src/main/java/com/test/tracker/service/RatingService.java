package com.test.tracker.service;

import com.test.tracker.model.dto.RatingResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    public RatingResponse getUserRating(Long userId) {
        return RatingResponse.builder()
                .rating(4.6f)
                .build();
    }

    public Map<Long, Float> getRatingForUserList(List<Long> users) {
        Map<Long, Float> response = new HashMap<>();
        users.forEach(userId -> response.put(userId, 4.6f));
        return response;
    }
}
