package com.nuzhd.taskmanagementsystem.controller;

import com.nuzhd.taskmanagementsystem.dto.*;
import com.nuzhd.taskmanagementsystem.mapper.TaskMapper;
import com.nuzhd.taskmanagementsystem.model.Task;
import com.nuzhd.taskmanagementsystem.service.TaskService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task", description = "Task management API")
@SecurityRequirement(name = "jwt_token")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Operation(
            summary = "Retrieve all tasks related to current user",
            description = "Returns all tasks based on the user id from the JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of user's tasks",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TaskDto.class)))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "User not found",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
    })
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasksByAuthor(@Parameter(description = "If provided, current user's assigned tasks will be retrieved") @RequestParam(value = "fetch_assigned", required = false) String fetchAssigned) {
        List<Task> foundTasks;
        if (fetchAssigned != null) {
            foundTasks = taskService.findAllTasksAssignedToUser();
        } else {
            foundTasks = taskService.findAllByAuthorId();
        }

        List<TaskDto> taskDtos = foundTasks.stream()
                .map(this.taskMapper::toDto)
                .toList();

        return ResponseEntity.ok(taskDtos);
    }

    @Operation(
            summary = "Create new task authored by current user",
            description = "Returns created task"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Saved task",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Validation errors",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Assignee doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Author doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
    })
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskCreateRequest createRequest) {
        Task newTask = taskMapper.fromCreateRequest(createRequest);

        TaskDto persistedTask = taskMapper.toDto(taskService.save(newTask));
        return ResponseEntity
                .status(201)
                .body(persistedTask);
    }

    @Operation(
            summary = "Update task details by its ID",
            description = "Returns updated task"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated task",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDto.class))
                    }
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
            ),
            @ApiResponse(responseCode = "403",
                    description = "Not enough permissions",
                    content = @Content
            )
    })
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable("taskId") Long taskId,
            @Valid @RequestBody TaskUpdateRequest updateRequest
    ) {
        TaskDto updatedTask = taskMapper.toDto(
                taskService.updateById(
                        taskId,
                        updateRequest
                )
        );

        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
            summary = "Delete task by its ID",
            description = "Returns empty body with 204 code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empty body returned in case of success",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Author/Task doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(responseCode = "403",
                    description = "Not enough permissions",
                    content = @Content
            )
    })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteById(taskId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(
            summary = "Assign task to another user by its ID",
            description = "Returns task with updated 'assigneeId' field"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated task",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Author/Task/Assignee doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(responseCode = "403",
                    description = "Not enough permissions",
                    content = @Content
            )
    })
    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<TaskDto> assignTask(@PathVariable("taskId") Long taskId,
                                              @Valid @RequestBody TaskAssignRequest assignRequest) {
        TaskDto updatedTask = taskMapper.toDto(
                taskService.assign(taskId, assignRequest.assigneeId())
        );

        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
            summary = "Update task status by its ID",
            description = "Returns task with updated 'status' field"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated task",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Author/Task doesn't exist",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(responseCode = "403",
                    description = "Not enough permissions",
                    content = @Content
            )
    })
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable("taskId") Long taskId,
                                                    @Valid @RequestBody TaskStatusUpdateRequest updateRequest) {

        TaskDto updatedTask = taskMapper.toDto(
                taskService.updateStatus(taskId, updateRequest.status())
        );

        return ResponseEntity.ok(updatedTask);
    }
}
