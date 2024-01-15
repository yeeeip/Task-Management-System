package com.nuzhd.taskmanagementsystem.dto;

import com.nuzhd.taskmanagementsystem.model.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dto containing info for creating new task")
public record TaskCreateRequest(
        @Schema(description = "Task title", example = "Refactor code", implementation = String.class)
        @NotBlank(message = "Title must not be blank or null")
        String title,

        @Schema(description = "Additional details of the task", implementation = String.class)
        String description,

        @Schema(description = "Task priority", example = "LOW", implementation = TaskPriority.class)
        @NotBlank(message = "Priority must not be blank or null")
        String priority,

        @Schema(description = "The id of the user this task will be assigned to", example = "10", implementation = Long.class)
        @NotNull(message = "Assignee id must not be null")
        Long assigneeId) {
}
