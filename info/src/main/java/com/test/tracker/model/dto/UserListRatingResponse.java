package com.test.tracker.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListRatingResponse {

    private Long id;
    private Float rating;

}
