package com.nuzhd.taskmanagementsystem.mapper;

import com.nuzhd.taskmanagementsystem.dto.TaskCommentDto;
import com.nuzhd.taskmanagementsystem.model.TaskComment;
import org.springframework.stereotype.Component;

@Component
public class TaskCommentMapper {

    public TaskCommentDto toDto(TaskComment comment) {
        return new TaskCommentDto(
                comment.getId(),
                comment.getAuthorId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

}
