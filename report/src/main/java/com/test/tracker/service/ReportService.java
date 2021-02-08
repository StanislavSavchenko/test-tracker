package com.test.tracker.service;

import com.test.tracker.model.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final TaskService taskService;

    @Autowired
    public ReportService(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<TaskEntity> buildReport() {
        return taskService.getAll();
    }

}

