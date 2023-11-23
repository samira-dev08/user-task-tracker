package com.company.service;

import com.company.dto.request.TaskRequest;
import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.enums.PriorityStatus;
import com.company.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(TaskRequest taskRequest, Integer userId);

    void deleteTask(Integer taskId);

    Task updateTask(TaskRequest taskRequest, Integer taskId, Integer userId);
}
