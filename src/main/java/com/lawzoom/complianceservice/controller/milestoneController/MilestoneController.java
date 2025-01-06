package com.lawzoom.complianceservice.controller.milestoneController;


import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequestForFetch;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneResponse;
import com.lawzoom.complianceservice.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/milestone")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @PostMapping("/create")
    public ResponseEntity<MilestoneResponse> createMilestone(@RequestBody MilestoneRequest milestoneRequest) {
        MilestoneResponse response = milestoneService.createMilestone(milestoneRequest);
        return ResponseEntity.status(201).body(response);
    }


    @PostMapping("/fetch-all-milestone")
    public ResponseEntity<List<MilestoneResponse>> fetchAllMilestones(@RequestBody MilestoneRequestForFetch milestoneRequestForFetch) {
        List<MilestoneResponse> responseList = milestoneService.fetchAllMilestones(milestoneRequestForFetch);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/fetchById/")
    public ResponseEntity<MilestoneResponse> fetchMilestoneById(@RequestParam Long milestoneId) {
        MilestoneResponse response = milestoneService.fetchMilestoneById(milestoneId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status-milestone/")
    public ResponseEntity<List<MilestoneResponse>> statusMileStone(
            @RequestParam Long userId,
            @RequestParam Long subscriberId,
            @RequestParam String status) {
        try {
            // Fetch milestones based on status
            List<MilestoneResponse> milestones = milestoneService.fetchMilestonesByStatus(userId, subscriberId, status);
            return ResponseEntity.ok(milestones);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }




}
