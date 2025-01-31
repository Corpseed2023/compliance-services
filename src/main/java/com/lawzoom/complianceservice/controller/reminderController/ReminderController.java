package com.lawzoom.complianceservice.controller.reminderController;

import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;
import com.lawzoom.complianceservice.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/compliance/reminder")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/save")
    public ResponseEntity<ReminderResponse> createOrUpdateReminder(
            @Valid @RequestBody ReminderRequest reminderRequest) {

        ReminderResponse response = reminderService.createOrUpdateReminder(reminderRequest);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<Map<String, Object>> fetchAllRemindersByUserId(@RequestParam("userId") Long userId) {
        Map<String, Object> response = reminderService.fetchAllRemindersByUserId(userId);
        return ResponseEntity.ok(response);
    }



}
