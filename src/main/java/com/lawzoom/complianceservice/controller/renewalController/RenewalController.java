package com.lawzoom.complianceservice.controller.renewalController;


import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import com.lawzoom.complianceservice.service.RenewalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/compliance/renewal")
public class RenewalController {

    @Autowired
    private RenewalService renewalService;

    // Endpoint for creating a new renewal
    @PostMapping("/create-compliance-renewal")
    public ResponseEntity<RenewalResponse> createRenewal(
            @RequestParam("complianceId") Long complianceId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        RenewalResponse response = renewalService.createRenewalForCompliance(complianceId, renewalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint for updating an existing renewal
    @PostMapping("/update")
    public ResponseEntity<RenewalResponse> createOrUpdateRenewal(
            @RequestParam("complianceId") Long complianceId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        RenewalResponse response = renewalService.generateComplianceRenewal(complianceId, renewalRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{complianceId}")
    public ResponseEntity<RenewalResponse> getRenewalByComplianceId(@PathVariable Long complianceId) {
        RenewalResponse response = renewalService.getRenewalByComplianceId(complianceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{complianceId}")
    public ResponseEntity<Void> deleteRenewal(@PathVariable Long complianceId) {
        renewalService.deleteRenewal(complianceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-milestone-renewal")
    public ResponseEntity<MilestoneRenewalResponse> createMilestoneRenewal(
            @RequestParam("milestoneId") Long milestoneId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        MilestoneRenewalResponse response = renewalService.createMilestoneRenewal(milestoneId, renewalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




}


