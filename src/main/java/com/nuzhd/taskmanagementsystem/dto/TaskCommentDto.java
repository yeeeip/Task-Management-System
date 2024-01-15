package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dto for representing concrete task comment")
public record TaskCommentDto(
        @Schema(description = "Comment ID", example = "28", implementation = Long.class)
        Long id,
        @Schema(description = "Comment author ID", example = "10", implementation = Long.class)
        Long authorId,
        @Schema(description = "Comment content", example = "Sounds good", implementation = String.class)
        String content,
        @Schema(description = "Comment creation date", example = "2024-01-14T23:51:58.366+03:00", implementation = LocalDateTime.class)
        LocalDateTime createdAt) {
}
