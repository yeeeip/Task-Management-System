package com.nuzhd.taskmanagementsystem.repo;

import com.nuzhd.taskmanagementsystem.model.TaskComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    List<TaskComment> findByTargetTaskId(Long taskId, PageRequest pageRequest);

}
