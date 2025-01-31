package com.lawzoom.complianceservice.controller.taskController;


import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;
import com.lawzoom.complianceservice.dto.taskDto.TaskUpdateRequest;
import com.lawzoom.complianceservice.service.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskListResponse> createTask(@RequestParam Long userid,@RequestBody TaskRequest taskRequest) {
        TaskListResponse taskResponse = taskService.createTask(taskRequest,userid);
        return ResponseEntity.status(201).body(taskResponse);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<TaskListResponse>> fetchTasks(@RequestParam Long milestoneId) {
        List<TaskListResponse> taskResponses = taskService.fetchTasks(milestoneId);
        return ResponseEntity.ok(taskResponses);
    }


    @GetMapping("/all-task")
    public ResponseEntity<Map<String, Object>> allTask(
            @RequestParam Long userId,
            @RequestParam Long subscriberId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        try {
            // Adjust page number to 0-based indexing if the user sends 1-based page number
            int adjustedPage = Math.max(page - 1, 0);

            // Create pageable object for pagination
            Pageable pageable = PageRequest.of(adjustedPage, size);

            // Fetch milestones with pagination
            Map<String, Object> response = taskService.fetchAllTask(userId, subscriberId, pageable);

            // Return the response as Map
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle user-related errors
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // Handle unexpected errors
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PutMapping("/update-task")
    public ResponseEntity<Map<String, Object>> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        Map<String, Object> response = taskService.updateTask(taskUpdateRequest);
        return ResponseEntity.ok(response);
    }



}
