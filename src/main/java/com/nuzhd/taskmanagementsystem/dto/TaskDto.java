package com.nuzhd.taskmanagementsystem.dto;

import com.nuzhd.taskmanagementsystem.model.TaskPriority;
import com.nuzhd.taskmanagementsystem.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Dto for representing tasks")
public record TaskDto(@Schema(description = "Task id", example = "123", implementation = Long.class)
                      Long id,

                      @Schema(description = "Task title", example = "Refactor code", implementation = String.class)
                      String title,

                      @Schema(description = "Additional details of the task", example = "Do not write unit tests", implementation = String.class)
                      String description,

                      @Schema(description = "Current status of the task", implementation = TaskStatus.class)
                      String status,

                      @Schema(description = "Task priority", implementation = TaskPriority.class)
                      String priority,

                      @Schema(description = "Task author ID", example = "10", implementation = Long.class)
                      Long authorId,

                      @Schema(description = "The ID of the user this task assigned to", example = "20", implementation = Long.class)
                      Long assigneeId,

                      @Schema(description = "List of comments related to this task")
                      Set<TaskCommentDto> comments) {
}
