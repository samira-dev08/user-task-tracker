package com.company.service;

import com.company.dto.request.TaskRequest;
import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.enums.PriorityStatus;
import com.company.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getTasksByCategoryId( Integer categoryId);

    Task createTask(TaskRequest taskRequest, Integer userId);
    List<TaskResponse> searchTask(TaskStatus status);

    void deleteTask(Integer taskId);

    void updateTask(TaskRequest taskRequest, Integer taskId);

    List<Category> getCategoriesByTaskId(Integer taskId);
}
