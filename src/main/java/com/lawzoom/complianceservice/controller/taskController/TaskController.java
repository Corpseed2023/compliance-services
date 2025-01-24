package com.lawzoom.complianceservice.controller.taskController;


import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;
import com.lawzoom.complianceservice.service.taskService.TaskService;
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
    public ResponseEntity<TaskListResponse> createTask(@RequestParam Long userid,@RequestBody TaskRequest taskRequest) {
        TaskListResponse taskResponse = taskService.createTask(taskRequest,userid);
        return ResponseEntity.status(201).body(taskResponse);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<TaskListResponse>> fetchTasks(@RequestParam Long milestoneId) {
        List<TaskListResponse> taskResponses = taskService.fetchTasks(milestoneId);
        return ResponseEntity.ok(taskResponses);
    }

    @PutMapping("/update-assignment")
    public ResponseEntity<TaskListResponse> updateTaskAssignment(
            @RequestParam Long taskId,
            @RequestParam Long assigneeUserId,
            @RequestParam Long reporterUserId) {
        TaskListResponse updatedTask = taskService.updateTaskAssignment(taskId, assigneeUserId, reporterUserId);
        return ResponseEntity.ok(updatedTask);
    }



}
