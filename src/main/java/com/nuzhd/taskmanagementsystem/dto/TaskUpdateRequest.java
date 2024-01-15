package com.nuzhd.taskmanagementsystem.dto;

import com.nuzhd.taskmanagementsystem.model.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for updating the task")
public record TaskUpdateRequest(
        @Schema(description = "Task title", implementation = String.class)
        @NotBlank(message = "Title must not be blank or null")
        String title,
        @Schema(description = "Additional details of the task", implementation = String.class)
        @NotBlank(message = "Description must not be blank or null")
        String description,
        @Schema(description = "Task priority", implementation = TaskPriority.class)
        @NotBlank(message = "Priority must not be blank or null")
        String priority) {
}
