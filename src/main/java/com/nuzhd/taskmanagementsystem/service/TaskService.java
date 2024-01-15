package com.nuzhd.taskmanagementsystem.service;

import com.nuzhd.taskmanagementsystem.dto.TaskUpdateRequest;
import com.nuzhd.taskmanagementsystem.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> findAllByAuthorId();

    List<Task> findAllTasksAssignedToUser();


    Task save(Task task);

    Task findById(Long taskId);

    Task updateById(Long taskId, TaskUpdateRequest updateRequest);

    Task assign(Long taskId, Long assigneeId);

    void deleteById(Long taskId);

    void delete(Task task);

    Task updateStatus(Long taskId, String status);

    boolean existsById(Long taskId);
}
