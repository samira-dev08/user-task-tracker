package com.company.service.impl;

import com.company.dto.request.CategoryRequest;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.exception.CategoryNotFoundException;
import com.company.exception.TaskNotFoundException;
import com.company.mapper.TaskMapper;
import com.company.repository.CategoryRepository;
import com.company.repository.TaskRepository;
import com.company.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public Category create(CategoryRequest categoryRequest) {
        Category category = taskMapper.toCategory(categoryRequest);
        return categoryRepository.save(category);
    }

    @Override
    public void  addCategoryToTask(Integer taskId, List<CategoryRequest> categoryRequestList) {
        Task task = taskRepository.findByIdAndIsActive(taskId,1).orElseThrow(() -> new TaskNotFoundException("Task not found!"));
        for (CategoryRequest categoryRequest : categoryRequestList) {
            Category category = taskMapper.toCategory(categoryRequest);
            categoryRepository.save(category);
            task.getCategory().add(category);
        }
        taskRepository.save(task);
    }
    public void removeCategoryFromTask( Integer taskId, Integer categoryId) {
        Task task = taskRepository.findByIdAndIsActive(taskId,1)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
            if (task.getCategory().contains(category)) {
                task.getCategory().remove(category);
                categoryRepository.delete(category);
                taskRepository.save(task);
            } else {
                throw new CategoryNotFoundException("Task doesnt have this category!");
            }

        }
    }

