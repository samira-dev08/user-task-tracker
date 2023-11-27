package com.company.controller;

import com.company.dto.request.CategoryRequest;
import com.company.dto.request.TaskRequest;
import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.enums.TaskStatus;
import com.company.exception.TaskNotFoundException;
import com.company.service.CategoryService;
import com.company.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final  CategoryService categoryService;

    @GetMapping("/categories/{categoryId}/tasks")
    public List<TaskResponse> getTasksByCategoryId(@PathVariable Integer categoryId) {
        return taskService.getTasksByCategoryId(categoryId);
    }
    @GetMapping("/tasks/{taskId}/categories")
    public List<Category> getCategoriesByTaskId(@PathVariable Integer taskId) {
        return taskService.getCategoriesByTaskId(taskId);
    }
    @PostMapping("/task/create-task/{userId}")
    public ResponseEntity<?> createTask(@PathVariable Integer userId, @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest, userId);
        return new ResponseEntity<>("Successfully created task" , HttpStatus.CREATED);
    }
    @GetMapping("/task/find-task/{status}")
    public List<TaskResponse> searchTask(@PathVariable TaskStatus status) {
        List<TaskResponse> taskResponseList= taskService.searchTask(status);
        return taskResponseList;
    }

    @PostMapping("/tasks/{taskId}/add-category")
    public ResponseEntity<?> addCategoryToTask(@PathVariable Integer taskId, @RequestBody List<CategoryRequest> categoryRequestList){
        categoryService.addCategoryToTask(taskId,categoryRequestList);
        return new ResponseEntity<>("success added category to task",HttpStatus.CREATED);
    }

    @PostMapping("/update-task/{taskId}")
    public ResponseEntity<?> updateTask( @PathVariable Integer taskId,
                                        @RequestBody TaskRequest taskRequest) {
        taskService.updateTask(taskRequest,taskId);
        return new ResponseEntity<>("Successfully updated task", HttpStatus.OK);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId ){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("Successfully deleted task", HttpStatus.OK);
    }
    @DeleteMapping("/{taskId}/remove-category/{categoryId}")
    public ResponseEntity<?> removeCategoryFromTask(@PathVariable Integer taskId, @PathVariable Integer categoryId) {

        categoryService.removeCategoryFromTask(taskId,categoryId);
        return new ResponseEntity<>("Successfully deleted the category from task",HttpStatus.OK);
    }

}
