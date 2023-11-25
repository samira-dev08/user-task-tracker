package com.company.controller;

import com.company.dto.request.TaskRequest;
import com.company.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create-task/{userId}")
    public ResponseEntity<?> createTask(@PathVariable Integer userId, @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest, userId);
        return new ResponseEntity<>("Successfully created task" , HttpStatus.CREATED);
    }
    @PostMapping("/update-task/{userId}")
    public ResponseEntity<?> updateTask(@PathVariable Integer userId,
                                        @RequestParam Integer taskId,
                                        @RequestBody TaskRequest taskRequest) {
        taskService.updateTask(taskRequest,taskId, userId);
        return new ResponseEntity<>("Successfully updated task", HttpStatus.OK);
    }

    @DeleteMapping("/delete-task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId ){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("Successfully deleted task", HttpStatus.OK);
    }


}
