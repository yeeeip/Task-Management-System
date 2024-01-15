package com.nuzhd.taskmanagementsystem.repo;

import com.nuzhd.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAuthorId(Long authorId);

    List<Task> findAllByAssigneeId(Long assigneeId);

    Optional<Task> findByIdAndAuthorId(Long taskId, Long author);

}
