package com.nuzhd.taskmanagementsystem.controller;

import com.nuzhd.taskmanagementsystem.dto.TaskCommentCreateRequest;
import com.nuzhd.taskmanagementsystem.dto.TaskCommentDto;
import com.nuzhd.taskmanagementsystem.mapper.TaskCommentMapper;
import com.nuzhd.taskmanagementsystem.service.TaskCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "Task comments management API")
@SecurityRequirement(name = "jwt_token")
@RestController
@RequestMapping("/api/v1/tasks/{taskId}/comments")
public class TaskCommentController {

    private final TaskCommentService taskCommentService;
    private final TaskCommentMapper commentMapper;

    public TaskCommentController(TaskCommentService taskCommentService, TaskCommentMapper commentMapper) {
        this.taskCommentService = taskCommentService;
        this.commentMapper = commentMapper;
    }

    @Operation(
            summary = "Allows users to comment the tasks",
            description = "Returns created comment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = TaskCommentDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Validation errors",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Author/Task doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<TaskCommentDto> leaveComment(
            @PathVariable("taskId") Long taskId,
            @Valid @RequestBody TaskCommentCreateRequest createRequest
    ) {

        TaskCommentDto savedComment = commentMapper.toDto(
                taskCommentService.save(taskId, createRequest)
        );

        return ResponseEntity.ok(savedComment);
    }

    @Operation(
            summary = "Get all comments related to the task by task ID",
            description = "Returns list of comments related to certain task (supports pagination)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = TaskCommentDto.class))
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Task doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<TaskCommentDto>> fetchByTaskIdPageable(@PathVariable("taskId") Long taskId,
                                                                      @Parameter(description = "Page number") @RequestParam(defaultValue = "1") Integer page,
                                                                      @Parameter(description = "Page size") @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                                      @Parameter(description = "Sorting field") @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy
    ) {
        List<TaskCommentDto> commentsPage = taskCommentService.findAllByTaskId(
                        taskId, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy))
                )
                .stream()
                .map(commentMapper::toDto)
                .toList();

        return ResponseEntity.ok(commentsPage);
    }
}
