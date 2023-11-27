package com.company.service.impl;

import com.company.dto.request.TaskRequest;
import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.entity.User;
import com.company.enums.TaskStatus;
import com.company.exception.CategoryNotFoundException;
import com.company.exception.TaskNotFoundException;
import com.company.exception.UserNotFoundException;
import com.company.mapper.TaskMapper;
import com.company.repository.CategoryRepository;
import com.company.repository.TaskRepository;
import com.company.repository.UserRepository;
import com.company.service.CategoryService;
import com.company.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    public List<TaskResponse> getTasksByCategoryId(Integer categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
        List<Task> taskList = taskRepository.findTaskByCategoryId(categoryId).get();
        if (taskList.isEmpty()) {
          throw new TaskNotFoundException("Task not found for this category");
        } else {
            return taskList.stream().map(task -> taskMapper.toTaskResponse(task)).collect(Collectors.toList());
        }
    }

    @Override
    public Task createTask(TaskRequest taskRequest, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Task task = taskMapper.toTask(taskRequest);
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public List<TaskResponse> searchTask(TaskStatus status) {
        List<Task> taskList = taskRepository.findTaskByStatus(status).get();
        List<TaskResponse> taskResponseList = taskList.stream()
                .map(task -> taskMapper.toTaskResponse(task)).collect(Collectors.toList());
        if (!taskResponseList.isEmpty()) {
            return taskResponseList;
        } else {
            throw new TaskNotFoundException("task not found in this status!");
        }
    }

    @Override
    public void deleteTask(Integer taskId) {
        Task task = taskRepository.findByIdAndIsActive(taskId, 1)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setIsActive(0);
        taskRepository.save(task);
    }

    @Override
    public void updateTask(TaskRequest taskRequest, Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found!"));
        Task updated = taskMapper.updateTask(task, taskRequest);
        updated.setUpdatedAt(LocalDateTime.now());
         taskRepository.save(updated);
    }

    @Override
    public List<Category> getCategoriesByTaskId(Integer taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("not found task");
        }
        return
                categoryRepository.findCategoryByTasksId(taskId)
                        .orElseThrow(() -> new CategoryNotFoundException("not found category"));
    }
}
