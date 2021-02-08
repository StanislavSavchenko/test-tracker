package com.test.tracker.service;

import com.test.tracker.model.entity.TaskEntity;
import com.test.tracker.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskEntity> getAll(){
        return (List<TaskEntity>) taskRepository.findAll();
    }

}
