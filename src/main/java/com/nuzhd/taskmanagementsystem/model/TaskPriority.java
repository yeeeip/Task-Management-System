package com.nuzhd.taskmanagementsystem.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, hidden = true)
public enum TaskPriority {

    HIGH,
    MEDIUM,
    LOW

}
