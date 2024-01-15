package com.nuzhd.taskmanagementsystem.mapper;

import com.nuzhd.taskmanagementsystem.dto.TaskCreateRequest;
import com.nuzhd.taskmanagementsystem.dto.TaskDto;
import com.nuzhd.taskmanagementsystem.model.Task;
import com.nuzhd.taskmanagementsystem.model.TaskPriority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private final TaskCommentMapper commentMapper;

    public TaskMapper(TaskCommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getPriority().toString(),
                task.getAuthorId(),
                task.getAssigneeId(),
                task.getComments().stream()
                        .map(commentMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }

    public Task fromCreateRequest(TaskCreateRequest request) {
        return new Task(
                request.title(),
                request.description(),
                TaskPriority.valueOf(request.priority()),
                request.assigneeId()
        );
    }
}
