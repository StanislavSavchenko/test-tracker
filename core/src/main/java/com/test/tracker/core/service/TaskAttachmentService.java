package com.test.tracker.core.service;

import com.test.tracker.core.model.entity.TaskAttachmentEntity;
import com.test.tracker.core.repository.TaskAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskAttachmentService {

    private final TaskAttachmentRepository taskAttachmentRepository;

    @Autowired
    public TaskAttachmentService(TaskAttachmentRepository taskAttachmentRepository) {
        this.taskAttachmentRepository = taskAttachmentRepository;
    }

    public TaskAttachmentEntity save(TaskAttachmentEntity taskAttachmentEntity) {
        return taskAttachmentRepository.save(taskAttachmentEntity);
    }

    public Optional<TaskAttachmentEntity> findOneByIdAndTask(Long attachmentId, Long taskId) {
        return taskAttachmentRepository.findOneByIdAndTaskId(attachmentId, taskId);
    }

    public List<TaskAttachmentEntity> findAllByTaskId(Long id) {
        return taskAttachmentRepository.findAllByTaskId(id);
    }
}
