package com.test.tracker.core.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.tracker.core.model.entity.SubDivisionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SubdivisionDto {

    private List<TaskDto> task;
    @JsonProperty("subdivision_info")
    private SubDivisionEntity subdivisionInfo;
}
