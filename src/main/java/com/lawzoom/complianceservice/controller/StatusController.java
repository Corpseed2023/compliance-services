package com.lawzoom.complianceservice.controller;


import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    // API to create a new Status
    @PostMapping("/create")
    public ResponseEntity<Status> createStatus(@RequestParam String status) {
        Status createdStatus = statusService.createStatus(status);
        return ResponseEntity.ok(createdStatus);
    }


    // API to fetch all Status entries
    @GetMapping("/all")
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = statusService.getAllStatuses();
        return ResponseEntity.ok(statuses);
    }
}