package com.test.tracker.core.controller;

import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.service.FileSystemService;
import com.test.tracker.core.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/core/task")
public class TaskController {

    private final TaskService taskService;
    private final FileSystemService attachmentService;

    @Autowired
    public TaskController(TaskService taskService, FileSystemService attachmentService) {
        this.taskService = taskService;
        this.attachmentService = attachmentService;
    }

    @GetMapping(value = "/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskEntity getById(@PathVariable("task_id") Long taskId) {
        return taskService.getOne(taskId);
    }

    @PostMapping(value = "/{task_id}/attachment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadAttachment(@PathVariable("task_id") Long taskId, @RequestPart MultipartFile document) {
        attachmentService.upload(taskId, document);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{task_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
