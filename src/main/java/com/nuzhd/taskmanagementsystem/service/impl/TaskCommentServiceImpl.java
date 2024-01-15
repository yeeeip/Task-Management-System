package com.nuzhd.taskmanagementsystem.service.impl;

import com.nuzhd.taskmanagementsystem.dto.TaskCommentCreateRequest;
import com.nuzhd.taskmanagementsystem.exception.TaskNotFoundException;
import com.nuzhd.taskmanagementsystem.model.TaskComment;
import com.nuzhd.taskmanagementsystem.repo.TaskCommentRepository;
import com.nuzhd.taskmanagementsystem.service.AuthenticationService;
import com.nuzhd.taskmanagementsystem.service.TaskCommentService;
import com.nuzhd.taskmanagementsystem.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository commentRepository;
    private final AuthenticationService authService;
    private final TaskService taskService;

    public TaskCommentServiceImpl(TaskCommentRepository commentRepository, AuthenticationService authService, TaskService taskService) {
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.taskService = taskService;
    }

    @Override
    public List<TaskComment> findAllByTaskId(Long taskId, PageRequest pageRequest) {
        return commentRepository.findByTargetTaskId(taskId, pageRequest);
    }

    @Override
    public TaskComment save(Long taskId, TaskCommentCreateRequest createRequest) {
        Long authorId = authService.fetchUserIdFromAuthentication();

        if (!taskService.existsById(taskId)) {
            throw new TaskNotFoundException("Task doesn't exist!");
        }

        TaskComment comment = new TaskComment(createRequest.content(), authorId, taskId);
        return commentRepository.save(comment);
    }
}
