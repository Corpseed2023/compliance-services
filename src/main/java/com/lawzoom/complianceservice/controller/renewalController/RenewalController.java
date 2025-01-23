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

import java.util.List;

@RestController
@RequestMapping("/api/compliance/renewal")
public class RenewalController {

    @Autowired
    private RenewalService renewalService;


    @PostMapping("/create-milestone-renewal")
    public ResponseEntity<MilestoneRenewalResponse> createMilestoneRenewal(
            @RequestParam("milestoneId") Long milestoneId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        MilestoneRenewalResponse response = renewalService.createMilestoneRenewal(milestoneId, renewalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-milestone-renewal/")
    public ResponseEntity<MilestoneRenewalResponse> updateMilestoneRenewal(
            @RequestParam("renewalId") Long renewalId, @RequestParam("mileStoneId") Long mileStoneId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        MilestoneRenewalResponse response = renewalService.updateMilestoneRenewal(renewalId, renewalRequest,mileStoneId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{renewalId}")
    public ResponseEntity<RenewalResponse> getRenewalById(@PathVariable Long renewalId) {
        RenewalResponse response = renewalService.getRenewalById(renewalId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milestone")
    public ResponseEntity<List<RenewalResponse>> getRenewalsByMilestoneId(
            @RequestParam Long userid,
            @RequestParam Long milestoneId) {

        List<RenewalResponse> responses = renewalService.getRenewalsByMilestoneId(userid, milestoneId);
        return ResponseEntity.ok(responses);
    }




}


