package com.lawzoom.complianceservice.controller.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.BusinessActivityRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.BusinessActivityResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.service.industry.BusinessActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/business-activity")
@CrossOrigin(origins = "*", maxAge = 3600)

public class BusinessActivityController {

    @Autowired
    private BusinessActivityService businessActivityService;

    @PostMapping("/create-activity")
    public ResponseEntity<BusinessActivity> createBusinessActivity(
            @RequestParam String businessActivityName,
            @RequestParam Long userId,
            @RequestParam Long industrySubCategoryId) {
        BusinessActivity createdActivity = businessActivityService.createBusinessActivity(
                businessActivityName, userId, industrySubCategoryId);
        return ResponseEntity.status(201).body(createdActivity);
    }

    @PutMapping("/update-activity")
    public ResponseEntity<BusinessActivity> updateBusinessActivity(@RequestBody BusinessActivityRequestDTO businessActivityRequestDTO) {
        BusinessActivity updatedActivity = businessActivityService.updateBusinessActivity(businessActivityRequestDTO);
        return ResponseEntity.ok(updatedActivity);
    }

    @GetMapping("/list-activity")
    public ResponseEntity<List<BusinessActivityResponseDTO>> getAllEnabledAndNonDeletedActivities(
            @RequestParam Long industrySubCategoryId) {
        List<BusinessActivityResponseDTO> activities =
                businessActivityService.getAllEnabledAndNonDeletedActivities(industrySubCategoryId);
        return ResponseEntity.ok(activities);
    }


    @DeleteMapping("/soft-delete")
    public ResponseEntity<String> softDeleteBusinessActivity(@RequestParam Long id) {
        boolean isDeleted = businessActivityService.softDeleteBusinessActivity(id);
        if (isDeleted) {
            return ResponseEntity.ok("Business activity successfully marked as deleted.");
        } else {
            return ResponseEntity.status(404).body("Business activity not found or already deleted.");
        }
    }

    @GetMapping("/active-business-activities")
    public ResponseEntity<List<BusinessActivityResponseDTO>> getActiveBusinessActivities(@RequestParam(required = false) String searchTerm) {
        List<BusinessActivityResponseDTO> activities = businessActivityService.getActiveBusinessActivities(searchTerm);
        return ResponseEntity.ok(activities);
    }



}
