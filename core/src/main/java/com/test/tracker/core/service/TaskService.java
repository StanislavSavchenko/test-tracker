package com.test.tracker.core.service;

import com.test.tracker.core.exception.EntityNotFoundException;
import com.test.tracker.core.model.entity.TaskAttachmentEntity;
import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.repository.TaskRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final FileSystemService fileSystemService;
    private final TaskAttachmentService taskAttachmentService;

    @Autowired
    public TaskService(TaskRepository taskRepository, FileSystemService fileSystemService, TaskAttachmentService taskAttachmentService) {
        this.taskRepository = taskRepository;
        this.fileSystemService = fileSystemService;
        this.taskAttachmentService = taskAttachmentService;
    }

    public List<TaskEntity> getList() {
        return (List<TaskEntity>) taskRepository.findAll();
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

    @Transactional(rollbackFor = Exception.class)
    public Long uploadAttachment(MultipartFile document, Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("task with id = " + taskId + " doesn't exist"));

        String documentPath = RandomStringUtils.randomAlphabetic(15);
        String fileExtension = FilenameUtils.getExtension(document.getResource().getFilename());

        fileSystemService.upload(documentPath, fileExtension, document);

        TaskAttachmentEntity taskAttachmentEntity = new TaskAttachmentEntity();
        taskAttachmentEntity.setName(document.getResource().getFilename());
        taskAttachmentEntity.setPath(documentPath + "." + fileExtension);
        taskAttachmentEntity.setTask(taskEntity);

        taskAttachmentService.save(taskAttachmentEntity);

        return taskAttachmentEntity.getId();
    }

    public File getAttachment(Long attachmentId, Long taskId) {
        TaskAttachmentEntity taskAttachmentEntity = taskAttachmentService.findOneByIdAndTask(attachmentId, taskId)
                .orElseThrow(() -> new EntityNotFoundException("attachment with id = " + attachmentId + " doesn't exist"));

        return fileSystemService.readFile(taskAttachmentEntity.getPath(), taskAttachmentEntity.getName());
    }

}
