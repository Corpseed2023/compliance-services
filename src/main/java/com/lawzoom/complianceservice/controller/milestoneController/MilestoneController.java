package com.lawzoom.complianceservice.controller.milestoneController;


import com.lawzoom.complianceservice.dto.complianceTaskDto.*;
import com.lawzoom.complianceservice.service.mileStoneService.MilestoneService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
@RequestMapping("/api/compliance/milestone")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createMilestone(@RequestBody MilestoneRequest milestoneRequest) {
        return milestoneService.createMilestone(milestoneRequest);
    }

//    @PostMapping("/fetch-all-milestone")
//    public ResponseEntity<List<MilestoneListResponse>> fetchAllMilestones(@RequestBody MilestoneRequestForFetch milestoneRequestForFetch) {
//        List<MilestoneListResponse> responseList = milestoneService.fetchAllMilestones(milestoneRequestForFetch);
//        return ResponseEntity.ok(responseList);
//    }

    @GetMapping("/fetchById")
    public ResponseEntity<Map<String, Object>> fetchMilestoneById(@RequestParam Long milestoneId) {
        try {
            Map<String, Object> response = milestoneService.fetchMilestoneById(milestoneId);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch milestone"));
        }
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

    @PutMapping("/update-milestone")
    public ResponseEntity<Map<String, Object>> updateMilestone(
            @RequestBody @Valid MilestoneUpdateRequest updateRequest) {
        try {
            Map<String, Object> response = milestoneService.updateMilestone(updateRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update milestone"));
        }
    }



    @PutMapping("/update-status")
    public ResponseEntity<MilestoneResponse> updateMilestoneStatus(
            @RequestParam Long milestoneId,
            @RequestParam Long statusId) {
        MilestoneResponse updatedMilestone = milestoneService.updateMilestoneStatus(milestoneId, statusId);
        return ResponseEntity.ok(updatedMilestone);
    }


    @GetMapping("/all-milestones")
    public ResponseEntity<Map<String, Object>> allMileStones(
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
            Map<String, Object> response = milestoneService.fetchUserAllMilestones(userId, subscriberId, pageable);

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






}
