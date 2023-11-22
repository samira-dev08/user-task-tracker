package com.company.service.impl;

import com.company.dto.request.CategoryRequest;
import com.company.dto.request.TaskRequest;
import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.entity.User;
import com.company.enums.PriorityStatus;
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
    private final CategoryService categoryService;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    public TaskResponse getTaskById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("task not found in this id"));
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> taskMapper.toTaskResponse(task))
                .collect(Collectors.toList());
    }


    @Override
    public Task createTask(TaskRequest taskRequest, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        List<Category> categories =taskRequest.getCategories().stream()
                .map(categoryRequest -> taskMapper.toCategory(categoryRequest)).collect(Collectors.toList());

        if (categories.size() != 0) {
             categories.stream().map((category -> {
                if (!categoryRepository.existsByNameEqualsIgnoreCase(category.getName())) {
                     categoryService.create(category);
                }
                return category;
            }));
        }
        Task task = taskMapper.toTask(taskRequest);
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setCategory(categories);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer taskId) {
        Task task=taskRepository.findByIdAndIsActive(taskId,1)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setIsActive(0);
        taskRepository.save(task);
    }

    @Override
    public Task updateTask(TaskRequest taskRequest, Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found!"));

        List<Category> categories =taskRequest.getCategories().stream()
                .map(categoryRequest -> taskMapper.toCategory(categoryRequest)).collect(Collectors.toList());
        if (categories.size() != 0) {
            categories.stream().map((category -> {
                if (!(categoryRepository.existsByNameEqualsIgnoreCase(category.getName()))) {
                    categoryService.create(category);
                }
                return category;
            }));
        }

       Task updated= taskMapper.updateTask(task,taskRequest);
        updated.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(updated);
    }
    @Override
    public List<Category> getAllCategoriesByTaskId(Integer taskId) {//("/tasks/{taskId}/categories")
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("not found task");
        }
        return
                taskRepository.findCategoryById(taskId)
                        .orElseThrow(() -> new CategoryNotFoundException("not found category"));
    }


    @Override
    public List<TaskResponse> getTasksByStatus(TaskStatus taskStatus) {
        return null;
    }

    @Override
    public List<TaskResponse> getTasksByPriority(PriorityStatus priorityStatus) {
        return null;
    }
}
