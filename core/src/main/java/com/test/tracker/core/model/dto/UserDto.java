package com.test.tracker.core.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.tracker.core.model.entity.UserEntity;
import lombok.Data;

@Data
public class UserDto {

    private UserEntity user;
    @JsonProperty("meta_info")
    private InfoServiceResponse metaInfo;

    public static UserDto mapToDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setUser(entity);

        return dto;
    }
}
