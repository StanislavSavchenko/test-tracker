package com.test.tracker.core.model.dto;

import com.test.tracker.core.model.entity.CommentEntity;
import com.test.tracker.core.model.entity.TaskAttachmentEntity;
import lombok.Data;

import java.util.List;

@Data
public class TaskDetailsResponse {
    private Long id;
    private String name;
    private String topic;
    private String status;
    private String description;
    private Long authorId;
    private Long performerId;
    private List<CommentEntity> comments;
    private List<TaskAttachmentEntity> attachments;
}
