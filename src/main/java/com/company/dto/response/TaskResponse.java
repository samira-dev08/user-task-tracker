package com.company.dto.response;

import com.company.enums.PriorityStatus;
import com.company.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private String name;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private PriorityStatus priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
