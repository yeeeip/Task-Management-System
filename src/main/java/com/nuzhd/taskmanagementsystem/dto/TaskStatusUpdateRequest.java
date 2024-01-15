package com.nuzhd.taskmanagementsystem.dto;

import com.nuzhd.taskmanagementsystem.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for updating status of the task")
public record TaskStatusUpdateRequest(
        @Schema(description = "New task status", implementation = TaskStatus.class)
        @NotBlank(message = "Status must not be blank or null")
        String status) {
}
