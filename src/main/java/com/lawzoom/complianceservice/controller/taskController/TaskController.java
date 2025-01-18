package com.lawzoom.complianceservice.controller.taskController;


import com.lawzoom.complianceservice.dto.TaskRequest;
import com.lawzoom.complianceservice.dto.TaskResponse;
import com.lawzoom.complianceservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return ResponseEntity.status(201).body(taskResponse);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<TaskResponse>> fetchTasks(@RequestParam Long milestoneId) {
        List<TaskResponse> taskResponses = taskService.fetchTasks(milestoneId);
        return ResponseEntity.ok(taskResponses);
    }

    @PutMapping("/update-assignment")
    public ResponseEntity<TaskResponse> updateTaskAssignment(
            @RequestParam Long taskId,
            @RequestParam Long assigneeUserId,
            @RequestParam Long reporterUserId) {
        TaskResponse updatedTask = taskService.updateTaskAssignment(taskId, assigneeUserId, reporterUserId);
        return ResponseEntity.ok(updatedTask);
    }

}
