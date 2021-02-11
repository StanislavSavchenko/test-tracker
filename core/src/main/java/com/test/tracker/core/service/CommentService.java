package com.test.tracker.core.service;

import com.test.tracker.core.exception.EntityNotFoundException;
import com.test.tracker.core.model.entity.CommentEntity;
import com.test.tracker.core.model.entity.TaskEntity;
import com.test.tracker.core.model.entity.UserEntity;
import com.test.tracker.core.repository.CommentRepository;
import com.test.tracker.core.repository.TaskRepository;
import com.test.tracker.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public CommentEntity create(Long authorId, Long taskId, CommentEntity request) {
        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("task not found"));

        request.setAuthor(author);
        request.setTask(task);
        request.setAuthorId(authorId);
        request.setTaskId(taskId);

        return commentRepository.save(request);
    }

    public void delete(Long id) {
        CommentEntity comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("comment not found"));

        commentRepository.delete(comment);
    }

    public List<CommentEntity> findAllByTaskId(Long id) {
        return commentRepository.findAllByTaskId(id);
    }
}
