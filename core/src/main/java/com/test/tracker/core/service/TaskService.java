package com.test.tracker.core.service;

import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.repository.TaskRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskEntity> getList() {
        return taskRepository.findAll();
    }

    public TaskEntity getOne(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));
    }

    public TaskEntity create(TaskEntity request) {
        return taskRepository.save(request);
    }

    public TaskEntity update(Long id, TaskEntity request) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));

        if (StringUtils.isNotEmpty(request.getStatus())) {
            task.setStatus(request.getStatus());
        }

        if (request.getPerformerId() != null) {
            task.setPerformerId(request.getPerformerId());
        }

        return taskRepository.save(task);
    }
}
