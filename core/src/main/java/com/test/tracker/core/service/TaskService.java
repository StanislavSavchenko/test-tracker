package com.test.tracker.core.service;

import com.test.tracker.core.exception.EntityNotFoundException;
import com.test.tracker.core.model.dto.InfoServiceResponse;
import com.test.tracker.core.model.dto.SubdivisionDto;
import com.test.tracker.core.model.dto.TaskDto;
import com.test.tracker.core.model.entity.SubDivisionEntity;
import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.repository.SubDivisionRepository;
import com.test.tracker.core.repository.TaskRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public TaskService(TaskRepository taskRepository, FileSystemService fileSystemService, SubDivisionRepository subDivisionRepository, AdditionalUserInfoService additionalUserInfoService) {
        this.taskRepository = taskRepository;
        this.fileSystemService = fileSystemService;
        this.subDivisionRepository = subDivisionRepository;
        this.additionalUserInfoService = additionalUserInfoService;
    }

    public SubdivisionDto getAll(Long subdivisionId) {
        if (subdivisionId == null) {
            return new SubdivisionDto(TaskDto.mapToDtoList(taskRepository.findAllByAndOrderByCreatedDt()), null);
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

        Map<Long, InfoServiceResponse> userMetaInfoMap = additionalUserInfoService.getUserInfo(userIdSet);
        taskDtoList.forEach(taskDto -> {
            taskDto.getAuthor().setMetaInfo(userMetaInfoMap.get(taskDto.getAuthor().getUser().getId()));
            taskDto.getPerformer().setMetaInfo(userMetaInfoMap.get(taskDto.getPerformer().getUser().getId()));
        });

        return new SubdivisionDto(taskDtoList, subDivision);
    }

    public TaskEntity getOne(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("task not found"));
    }

    public TaskEntity create(TaskEntity request) {
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
    public void uploadAttachment() {

    }
}
