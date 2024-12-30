package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import org.springframework.web.server.ResponseStatusException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @Autowired
    private ComplianceRepo complianceRepository;


    @GetMapping("/showAllCompliance")
    public List<ComplianceResponse> fetchAllCompliance(@RequestParam("companyId") Long companyId,
                                                       @RequestParam("businessId") Long businessUnitId) {
        if (companyId == null || businessUnitId == null) {
            throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
        }
        return complianceService.fetchAllCompliances(companyId, businessUnitId);
    }

    @GetMapping("/getComplianceCount")
    public Map<Long,Integer> getComplianceCount(){

        System.out.println("API Hiting by auth");

        return complianceService.getComplianceCount();

    }

    @GetMapping("/company/getComplianceCounts")
    public Map<Long, Map<Long, Integer>> getComplianceCountsByCompanyAndBusinessUnit() {
        return complianceService.getComplianceCountsByCompanyAndBusinessUnit();
    }

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
    public ResponseEntity<ComplianceResponse> updateCompliance(
            @Valid @RequestBody ComplianceRequest complianceRequest,
            @RequestParam("businessUnitId") Long businessUnitId,@RequestParam Long complianceId) {

        if (businessUnitId == null) {
            throw new IllegalArgumentException("Please provide a valid businessUnitId");
        }

        try {
            ComplianceResponse response = complianceService.updateCompliance(complianceRequest, businessUnitId,complianceId);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e; // Let Spring handle the HTTP status and message
        } catch (Exception e) {
            throw new RuntimeException("Failed to update compliance: " + e.getMessage(), e);
        }
    }


    @GetMapping("/fetchByBusinessUnit")
    public ResponseEntity<List<ComplianceResponse>> fetchCompliancesByBusinessUnit(
            @RequestParam("businessUnitId") Long businessUnitId) {

        if (businessUnitId == null) {
            throw new IllegalArgumentException("Please provide a valid businessUnitId");
        }

        try {
            List<ComplianceResponse> responseList = complianceService.fetchCompliancesByBusinessUnit(businessUnitId);
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch compliances: " + e.getMessage(), e);
        }
    }

    @GetMapping("/getAllComplianceByCompanyUnitTeam")
    public ComplianceResponse getAllComplianceByCompanyUnitTeam(@RequestParam("companyId") Long companyId,
                                                                @RequestParam("businessUnitId") Long businessUnitId,
                                                                @RequestParam("teamId") Long teamId) {
        if (companyId == null || businessUnitId == null || teamId == null) {
            throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
        }
        return this.complianceService.getAllComplianceByCompanyUnitTeam(teamId, companyId, businessUnitId);

    }

//    @GetMapping("/getAllComplianceByUserId")
//    public ResponseEntity<Map<Long, List<ComplianceResponse>>> getAllComplianceByUserId() {
//        Map<Long, List<ComplianceResponse>> result = complianceService.getAllComplianceByCompanyId();
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

//    @GetMapping("/compliance-count")
//    public List<Map<String, Object>> getComplianceCountPerCompanyAndBusinessUnit() {
//
//        System.out.println("Got hit by authentication");
//        List<Object[]> result = complianceRepository.countCompliancePerCompanyAndBusinessUnit();
//
//        return result.stream()
//                .map(objects -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("companyId", objects[0]);
//                    map.put("businessUnitId", objects[1]);
//                    map.put("complianceCount", objects[2]);
//                    return map;
//                })
//                .collect(Collectors.toList());
//    }



}