package com.lawzoom.complianceservice.controller.taskController;

import com.lawzoom.complianceservice.dto.taskDto.TaskReminderUpdateRequest;
import com.lawzoom.complianceservice.service.taskService.TaskReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/compliance/task-reminder")
public class TaskReminderController {

    @Autowired
    private TaskReminderService taskReminderService;

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateTaskReminder(
            @RequestParam Long taskReminderId,
            @Valid @RequestBody TaskReminderUpdateRequest updateRequest) {

        Map<String, Object> response = taskReminderService.updateTaskReminder(taskReminderId, updateRequest);
        return ResponseEntity.ok(response);
    }
}
