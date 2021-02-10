package com.test.tracker.core.controller;

import com.test.tracker.core.model.dto.SubdivisionDto;
import com.test.tracker.core.model.dto.TaskDetailsResponse;
import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.service.TaskService;
import com.test.tracker.core.util.DocumentUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Api
@RestController
@RequestMapping("/core/task")
public class TaskController {

    private final TaskService taskService;
    private final ServletContext servletContext;

    @Autowired
    public TaskController(TaskService taskService, ServletContext servletContext) {
        this.taskService = taskService;
        this.servletContext = servletContext;
    }

    @GetMapping(value = "/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDetailsResponse getById(@PathVariable("task_id") Long taskId) {
        return taskService.getDetails(taskId);
    }

    @PostMapping(value = "/{task_id}/attachment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadAttachment(@PathVariable("task_id") Long taskId, @RequestPart MultipartFile document) {
        Long attachmentId = taskService.uploadAttachment(document, taskId);
        URI uri = fromCurrentRequest().path("/{attachment_id}").replaceQuery("").buildAndExpand(attachmentId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{task_id}/attachment/{attachment_id}")
    public void getAttachment(
            @PathVariable("task_id") Long taskId,
            @PathVariable("attachment_id") Long attachmentId,
            HttpServletResponse response
    ) {
        File file = taskService.getAttachment(attachmentId, taskId);
        MediaType mediaType = getFileMediaType(file.getName());
        DocumentUtil.attachDocumentToResponse(file, response, mediaType.toString());
    }

    private MediaType getFileMediaType(String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
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

    @GetMapping()
    public SubdivisionDto getAll(@RequestParam(value = "subdivision_id", required = false) Long subdivision_id) {
        return taskService.getAll(subdivision_id);
    }
}
