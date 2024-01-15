package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dto for providing comment content when leaving comment")
public record TaskCommentCreateRequest(
        @Schema(description = "The comment content", example = "Very good!", implementation = String.class)
        @NotBlank(message = "Comment must not be blank or null")
        @Size(min = 8, max = 400, message = "Comment must contain from 8 to 400 symbols")
        String content
) {
}
