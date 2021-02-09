package com.test.tracker.core.model.dto;

import com.test.tracker.core.model.entity.TaskEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskDto {

    private Long id;
    private String name;
    private String topic;
    private String status;
    private String description;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private UserDto author;
    private UserDto performer;

    public static TaskDto mapToDto(TaskEntity entity) {
        TaskDto dto = new TaskDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTopic(entity.getTopic());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setCreatedDt(entity.getCreatedDt());
        dto.setUpdatedDt(entity.getUpdatedDt());

        dto.setAuthor(UserDto.mapToDto(entity.getAuthor()));
        dto.setPerformer(UserDto.mapToDto(entity.getPerformer()));

        return dto;
    }

    public static List<TaskDto> mapToDtoList(List<TaskEntity> entityList) {
        return entityList.stream().map(TaskDto::mapToDto).collect(Collectors.toList());
    }

}
