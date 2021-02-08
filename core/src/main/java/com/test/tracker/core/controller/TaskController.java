package com.test.tracker.core.controller;

import com.test.tracker.core.model.TaskEntity;
import com.test.tracker.core.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{task_id}")
    public TaskEntity getById(@PathVariable("task_id") Long taskId) {
        return taskService.getOne(taskId);
    }

    @PatchMapping("/{task_id}")
    public TaskEntity update(@PathVariable("task_id") Long taskId,
                             @RequestBody TaskEntity taskEntity) {
        return taskService.update(taskId, taskEntity);
    }

    @PostMapping
    public TaskEntity create(@RequestBody TaskEntity request) {
        return taskService.create(request);
    }
}
