package com.lawzoom.complianceservice.controller;


import com.lawzoom.complianceservice.dto.RenewalRequest;
import com.lawzoom.complianceservice.dto.RenewalResponse;
import com.lawzoom.complianceservice.service.RenewalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/renewal")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RenewalController {

    @Autowired
    private RenewalService renewalService;

    @PostMapping("/create-or-update")
    public ResponseEntity<RenewalResponse> createOrUpdateRenewal(
            @RequestParam("complianceId") Long complianceId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        RenewalResponse response = renewalService.createOrUpdateRenewal(complianceId, renewalRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch")
    public ResponseEntity<RenewalResponse> getRenewalByComplianceId(@RequestParam("complianceId") Long complianceId) {
        RenewalResponse response = renewalService.getRenewalByComplianceId(complianceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRenewal(@RequestParam("complianceId") Long complianceId) {
        renewalService.deleteRenewal(complianceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<RenewalResponse> createRenewal(
            @RequestParam("complianceId") Long complianceId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        // Call the service to create a renewal
        RenewalResponse response = renewalService.createRenewalForCompliance(complianceId, renewalRequest);
        return ResponseEntity.status(201).body(response);
    }

}
