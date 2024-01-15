package com.nuzhd.taskmanagementsystem.service;

import com.nuzhd.taskmanagementsystem.dto.TaskCommentCreateRequest;
import com.nuzhd.taskmanagementsystem.model.TaskComment;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TaskCommentService {

    List<TaskComment> findAllByTaskId(Long taskId, PageRequest pageRequest);

    TaskComment save(Long taskId, TaskCommentCreateRequest createRequest);

}
