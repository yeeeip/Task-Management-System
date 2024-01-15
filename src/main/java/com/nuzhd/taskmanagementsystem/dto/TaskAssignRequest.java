package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dto for providing assignee id when assigning task")
public record TaskAssignRequest(
        @Schema(example = "15", implementation = Long.class)
        @NotNull(message = "Assignee id must not be null or blank")
        Long assigneeId) {
}
