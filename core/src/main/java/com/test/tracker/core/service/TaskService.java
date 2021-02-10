package com.test.tracker.core.service;

import com.test.tracker.core.exception.EntityNotFoundException;
import com.test.tracker.core.model.dto.InfoServiceResponse;
import com.test.tracker.core.model.dto.SubdivisionDto;
import com.test.tracker.core.model.dto.TaskDetailsResponse;
import com.test.tracker.core.model.dto.TaskDto;
import com.test.tracker.core.model.entity.*;
import com.test.tracker.core.repository.SubDivisionRepository;
import com.test.tracker.core.repository.TaskRepository;
import com.test.tracker.core.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final FileSystemService fileSystemService;
    private final SubDivisionRepository subDivisionRepository;
    private final AdditionalUserInfoService additionalUserInfoService;
    private final TaskAttachmentService taskAttachmentService;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, FileSystemService fileSystemService, SubDivisionRepository subDivisionRepository, AdditionalUserInfoService additionalUserInfoService, TaskAttachmentService taskAttachmentService, CommentService commentService, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.fileSystemService = fileSystemService;
        this.subDivisionRepository = subDivisionRepository;
        this.additionalUserInfoService = additionalUserInfoService;
        this.taskAttachmentService = taskAttachmentService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }


    public SubdivisionDto getAll(Long subdivisionId) {
        if (subdivisionId == null) {
            return new SubdivisionDto(TaskDto.mapToDtoList(taskRepository.findAllByOrderByCreatedDt()), null);
        }
        SubDivisionEntity subDivision = subDivisionRepository.findById(subdivisionId)
                .orElseThrow(() -> new EntityNotFoundException("subdivision not found"));
        List<TaskEntity> taskEntityList = taskRepository.findAllBySubdivision(subdivisionId);
        List<TaskDto> taskDtoList = TaskDto.mapToDtoList(taskEntityList);

        Set<Long> userIdSet = new HashSet<>();
        taskDtoList.forEach((task) -> {
            userIdSet.add(task.getAuthor().getUser().getId());
            userIdSet.add(task.getPerformer().getUser().getId());
        });

        Map<Long, Float> userMetaInfoMap = additionalUserInfoService.getUsersInfo(userIdSet);
        taskDtoList.forEach(taskDto -> {
            taskDto.getAuthor().setMetaInfo(new InfoServiceResponse(userMetaInfoMap.get(taskDto.getAuthor().getUser().getId())));
            taskDto.getPerformer().setMetaInfo(new InfoServiceResponse(userMetaInfoMap.get(taskDto.getPerformer().getUser().getId())));
        });

        return new SubdivisionDto(taskDtoList, subDivision);
    }

    public TaskDetailsResponse getDetails(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));

        TaskDetailsResponse taskDetailsResponse = new TaskDetailsResponse();
        List<CommentEntity> commentEntityList = commentService.findAllByTaskId(id);
        List<TaskAttachmentEntity> taskAttachmentEntityList = taskAttachmentService.findAllByTaskId(id);

        taskDetailsResponse.setId(taskEntity.getId());
        taskDetailsResponse.setAuthorId(taskEntity.getAuthor().getId());
        taskDetailsResponse.setComments(commentEntityList);
        taskDetailsResponse.setAttachments(taskAttachmentEntityList);
        taskDetailsResponse.setName(taskEntity.getName());
        taskDetailsResponse.setPerformerId(taskEntity.getId());
        taskDetailsResponse.setStatus(taskEntity.getStatus());
        taskDetailsResponse.setTopic(taskEntity.getTopic());

        return taskDetailsResponse;

    }

    public TaskEntity create(Long authorId, Long performerId, TaskEntity request) {
        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("author not found"));
        UserEntity performer = userRepository.findById(performerId)
                .orElseThrow(() -> new EntityNotFoundException("performer not found"));

        request.setAuthor(author);
        request.setPerformer(performer);

        return taskRepository.save(request);
    }

    public TaskEntity update(Long id, TaskEntity request) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("task not found"));

        if (StringUtils.isNotEmpty(request.getStatus())) {
            task.setStatus(request.getStatus());
        }

        if (request.getPerformer() != null) {
            task.setPerformer(request.getPerformer());
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
