package com.lawzoom.complianceservice.controller.reminderController;


import com.lawzoom.complianceservice.dto.complianceReminder.ComplianceReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ComplianceReminderResponse;
import com.lawzoom.complianceservice.service.ComplianceReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/reminder")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ComplianceReminderController {

    @Autowired
    private ComplianceReminderService complianceReminderService;

    @PostMapping("/create")
    public ResponseEntity<ComplianceReminderResponse> createComplianceReminder(
            @RequestParam("complianceId") Long complianceId,
            @RequestParam("subscriberId") Long subscriberId,
            @Valid @RequestBody ComplianceReminderRequest reminderRequest) {

        ComplianceReminderResponse response = complianceReminderService.createComplianceReminder(
                complianceId, subscriberId, reminderRequest);

        return ResponseEntity.status(201).body(response);
    }


    @GetMapping("/fetch")
    public ResponseEntity<List<ComplianceReminderResponse>> fetchComplianceReminders(
            @RequestParam("complianceId") Long complianceId,
            @RequestParam("subscriberId") Long subscriberId) {

        List<ComplianceReminderResponse> responseList = complianceReminderService.fetchComplianceReminders(complianceId, subscriberId);
        return ResponseEntity.ok(responseList);
    }
}
