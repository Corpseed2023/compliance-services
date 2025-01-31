package com.lawzoom.complianceservice.controller.complianceController;



import com.lawzoom.complianceservice.dto.complianceDto.CompanyComplianceDTO;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/compliance/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @Autowired
    private ComplianceRepo complianceRepository;

    @PostMapping("/saveCompliance")
    public ResponseEntity<ComplianceResponse> saveCompliance(
            @Valid @RequestBody ComplianceRequest complianceRequest,
            @RequestParam("businessUnitId") Long businessUnitId,
            @RequestParam("userId") Long userId) {

        if (businessUnitId == null) {
            throw new IllegalArgumentException("Please provide a valid businessUnitId");
        }

        try {
            ComplianceResponse response = complianceService.saveCompliance(complianceRequest, businessUnitId, userId);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save compliance: " + e.getMessage(), e);
        }
    }


    @PutMapping("/updateCompliance")
    public ResponseEntity<Map<String, Object>> updateCompliance(
            @Valid @RequestBody ComplianceRequest complianceRequest,
            @RequestParam("businessUnitId") Long businessUnitId,
            @RequestParam Long complianceId,
            @RequestParam Long userId) {

        if (businessUnitId == null) {
            throw new IllegalArgumentException("Please provide a valid businessUnitId");
        }

        try {
            Map<String, Object> response = complianceService.updateCompliance(complianceRequest, businessUnitId, complianceId, userId);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update compliance: " + e.getMessage(), e);
        }
    }


    @GetMapping("/fetchByBusinessUnit")
    public ResponseEntity<List<ComplianceResponse>> fetchComplianceByBusinessUnit(
            @RequestParam("businessUnitId") Long businessUnitId,
            @RequestParam("userId") Long userId,
            @RequestParam("subscriberId") Long subscriberId) {

        if (businessUnitId == null || userId == null || subscriberId == null) {
            throw new IllegalArgumentException("Please provide valid businessUnitId, userId, and subscriberId");
        }

        try {
            List<ComplianceResponse> responseList = complianceService.fetchComplianceByBusinessUnit(businessUnitId, userId, subscriberId);
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch compliance: " + e.getMessage(), e);
        }
    }

    @GetMapping("/company-details-count")
    public ResponseEntity<List<CompanyComplianceDTO>> getCompanyComplianceDetails(
            @RequestParam Long userId,
            @RequestParam Long subscriberId) {

        if (userId == null || subscriberId == null) {
            throw new IllegalArgumentException("User ID and Subscriber ID must be provided.");
        }

        try {
            List<CompanyComplianceDTO> companyComplianceDetails = complianceService.getCompanyComplianceDetails(userId, subscriberId);
            return ResponseEntity.ok(companyComplianceDetails);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch compliance details: " + e.getMessage(), e);
        }
    }

    @GetMapping("/fetch-compliance")
    public ResponseEntity<Map<String, Object>> fetchComplianceById(@RequestParam("complianceId") Long complianceId) {
        try {
            Map<String, Object> response = complianceService.fetchComplianceById(complianceId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Compliance not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch compliance: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }






}