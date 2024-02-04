package com.nuzhd.taskmanagementsystem.service;

import com.nuzhd.taskmanagementsystem.dto.TaskUpdateRequest;
import com.nuzhd.taskmanagementsystem.exception.TaskNotFoundException;
import com.nuzhd.taskmanagementsystem.exception.UserNotFoundException;
import com.nuzhd.taskmanagementsystem.model.Task;
import com.nuzhd.taskmanagementsystem.model.TaskPriority;
import com.nuzhd.taskmanagementsystem.model.TaskStatus;
import com.nuzhd.taskmanagementsystem.repo.TaskRepository;
import com.nuzhd.taskmanagementsystem.service.impl.TaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private MyUserService userService;
    @Mock
    private AuthenticationService authService;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void TaskService_SaveTask_ReturnsSavedTask() {

        Task task = new Task(1L,
                "Test task",
                "description",
                TaskStatus.NEW,
                TaskPriority.LOW,
                2L,
                6L
        );

        when(userService.existsById(6L)).thenReturn(true);

        when(taskRepository.save(Mockito.any(Task.class)))
                .thenReturn(task);

        Task savedTask = taskService.save(task);

        assertThat(savedTask).isNotNull();
    }

    @Test
    public void TaskService_SaveTask_ThrowUserNotFoundException() {

        Task task = new Task(
                1L,
                "Test task",
                "description",
                TaskStatus.NEW,
                TaskPriority.LOW,
                2L,
                6L
        );

        assertThrows(
                UserNotFoundException.class,
                () -> taskService.save(task)
        );
    }

    @Test
    public void TaskService_FindById_ReturnsFoundTask() {

        Task task = new Task(
                1L,
                "Test task",
                "description",
                TaskStatus.NEW,
                TaskPriority.LOW,
                2L,
                6L
        );

        when(taskRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        Task foundTask = taskService.findById(task.getId());

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(task.getId());
    }

    @Test
    public void TaskService_FindById_ThrowsTaskNotFoundException() {

        Task task = new Task(
                1L,
                "Test task",
                "description",
                TaskStatus.NEW,
                TaskPriority.LOW,
                2L,
                6L
        );

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.findById(2L)
        );
    }

    @Test
    public void TaskService_UpdateById_ReturnsUpdatedTask() {

        TaskUpdateRequest updateRequest = new TaskUpdateRequest(
                "New title",
                "New Description",
                "HIGH"
        );

        Task taskToUpdate = new Task(
                1L,
                "Test title",
                "Test description",
                TaskStatus.NEW,
                TaskPriority.LOW,
                2L,
                6L
        );

        when(authService.fetchUserIdFromAuthentication())
                .thenReturn(taskToUpdate.getAuthorId());

        when(taskRepository.findById(taskToUpdate.getId()))
                .thenReturn(Optional.of(taskToUpdate));

        when(taskRepository.save(Mockito.any(Task.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Task updatedTask = taskService.updateById(taskToUpdate.getId(), updateRequest);

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask).isEqualTo(taskToUpdate);
    }
}
