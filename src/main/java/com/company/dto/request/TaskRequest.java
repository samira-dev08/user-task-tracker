package com.company.dto.request;

import com.company.entity.Category;
import com.company.enums.PriorityStatus;
import com.company.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class TaskRequest {
    private String name;
    private String description;
    private PriorityStatus priority;
    private LocalDateTime deadline;
    private TaskStatus status;
}
