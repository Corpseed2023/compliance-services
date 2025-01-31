package com.lawzoom.complianceservice.controller.reminderController;


import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;
import com.lawzoom.complianceservice.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/reminder")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/create")
    public ResponseEntity<ReminderResponse> createReminder(
            @RequestParam("complianceId") Long complianceId,
            @RequestParam("subscriberId") Long subscriberId,
            @Valid @RequestBody ReminderRequest reminderRequest) {

        ReminderResponse response = reminderService.createReminder(
                complianceId, subscriberId, reminderRequest);

        return ResponseEntity.status(201).body(response);
    }



}
