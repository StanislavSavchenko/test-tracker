package com.test.tracker.core.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.tracker.core.model.CommentEntity;
import com.test.tracker.core.model.views.Views;
import com.test.tracker.core.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView({Views.Retrieve.class})
    public CommentEntity create(@RequestParam("author_id") Long authorId,
                                @RequestParam("task_id") Long taskId,
                                @RequestBody @JsonView({Views.Update.class}) CommentEntity request) {
        return commentService.create(authorId, taskId, request);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Void> delete(@PathVariable("comment_id") Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
