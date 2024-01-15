package com.nuzhd.taskmanagementsystem.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task status",enumAsRef = true, hidden = true)
public enum TaskStatus {

    NEW,
    IN_PROGRESS,
    COMPLETED

}
