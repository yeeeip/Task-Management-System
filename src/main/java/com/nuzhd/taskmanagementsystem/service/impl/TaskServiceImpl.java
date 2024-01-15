package com.nuzhd.taskmanagementsystem.service.impl;

import com.nuzhd.taskmanagementsystem.dto.TaskUpdateRequest;
import com.nuzhd.taskmanagementsystem.exception.TaskNotFoundException;
import com.nuzhd.taskmanagementsystem.exception.NotEnoughPermissionsException;
import com.nuzhd.taskmanagementsystem.exception.UserNotFoundException;
import com.nuzhd.taskmanagementsystem.model.Task;
import com.nuzhd.taskmanagementsystem.model.TaskPriority;
import com.nuzhd.taskmanagementsystem.model.TaskStatus;
import com.nuzhd.taskmanagementsystem.repo.TaskRepository;
import com.nuzhd.taskmanagementsystem.service.AuthenticationService;
import com.nuzhd.taskmanagementsystem.service.MyUserService;
import com.nuzhd.taskmanagementsystem.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MyUserService userService;
    private final AuthenticationService authService;

    public TaskServiceImpl(TaskRepository taskRepository, MyUserService userService, AuthenticationService authService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByAuthorId() {
        Long userId = authService.fetchUserIdFromAuthentication();

        if (!userService.existsById(userId)) {
            throw new UserNotFoundException("User doesn't exist!");
        }

        return taskRepository.findAllByAuthorId(userId);
    }

    public List<Task> findAllTasksAssignedToUser() {
        Long userId = authService.fetchUserIdFromAuthentication();

        if (!userService.existsById(userId)) {
            throw new UserNotFoundException("User doesn't exist!");
        }

        return taskRepository.findAllByAssigneeId(userId);
    }

    @Override
    public Task save(Task task) {
        Long authorId = authService.fetchUserIdFromAuthentication();
        task.setAuthorId(authorId);

        if (!userService.existsById(task.getAssigneeId())) {
            throw new UserNotFoundException("Assignee with provided id doesn't exist!");
        }

        return taskRepository.save(task);
    }

    @Override
    public Task findById(Long taskId) {
        return findOrThrow(taskId);
    }

    @Override
    public Task updateById(Long taskId, TaskUpdateRequest updateRequest) {
        Long userId = authService.fetchUserIdFromAuthentication();

        Task updatableTask = findOrThrow(taskId);

        if (!Objects.equals(updatableTask.getAuthorId(), userId)) {
            throw new NotEnoughPermissionsException("You don't have permission to edit this task!");
        }

        updatableTask.setTitle(updateRequest.title());
        updatableTask.setDescription(updateRequest.description());
        updatableTask.setPriority(TaskPriority.valueOf(updateRequest.priority()));

        return taskRepository.save(updatableTask);
    }

    @Override
    public void deleteById(Long taskId) {
        Task deletableTask = findOrThrow(taskId);
        Long userId = authService.fetchUserIdFromAuthentication();

        if (!Objects.equals(deletableTask.getAuthorId(), userId)) {
            throw new NotEnoughPermissionsException("You don't have permission to delete this task!");
        }

        taskRepository.delete(deletableTask);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public Task assign(Long taskId, Long assigneeId) {
        Task taskToAssign = findOrThrow(taskId);
        Long authorId = authService.fetchUserIdFromAuthentication();

        if (!userService.existsById(assigneeId)) {
            throw new UserNotFoundException("Assignee with provided id doesn't exist");
        }

        if (!Objects.equals(taskToAssign.getAuthorId(), authorId)) {
            throw new NotEnoughPermissionsException("You don't have permission to assign this task!");
        }

        taskToAssign.setAssigneeId(assigneeId);

        return taskRepository.save(taskToAssign);
    }

    @Override
    public Task updateStatus(Long taskId, String status) {
        Task taskToUpdate = findOrThrow(taskId);
        Long userId = authService.fetchUserIdFromAuthentication();

        if (!Objects.equals(taskToUpdate.getAuthorId(), userId) &&
                !Objects.equals(taskToUpdate.getAssigneeId(), userId)) {
            throw new NotEnoughPermissionsException("You don't have permission to update the status of this task");
        }

        taskToUpdate.setStatus(TaskStatus.valueOf(status));

        return taskRepository.save(taskToUpdate);
    }

    @Override
    public boolean existsById(Long taskId) {
        return taskRepository.existsById(taskId);
    }

    private Task findOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task with id " + taskId + " not found"));
    }

}
