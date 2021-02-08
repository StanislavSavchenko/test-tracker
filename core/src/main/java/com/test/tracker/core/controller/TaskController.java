package com.test.tracker.core.controller;

import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity getById(@PathVariable("task_id") Long taskId) {
        return taskService.getOne(taskId);
    }

    @PatchMapping(value = "/{task_id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity update(@PathVariable("task_id") Long taskId,
                             @RequestBody TaskEntity taskEntity) {
        return taskService.update(taskId, taskEntity);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity create(@RequestBody TaskEntity request) {
        return taskService.create(request);
    }

    @GetMapping("/")
    public TaskEntity getAll(@PathVariable("task_id") Long taskId) {
        return taskService.getOne(taskId);
    }
}
