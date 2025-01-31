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

    @PostMapping("/save")
    public ResponseEntity<MilestoneRenewalResponse> createOrUpdateMilestoneRenewal(
            @RequestParam("milestoneId") Long milestoneId,
            @RequestParam(value = "renewalId", required = false) Long renewalId,
            @Valid @RequestBody RenewalRequest renewalRequest) {

        MilestoneRenewalResponse response = renewalService.createOrUpdateMilestoneRenewal(milestoneId, renewalRequest,renewalId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/fetch-renewal")
    public ResponseEntity<RenewalResponse> getRenewalById(@RequestParam Long renewalId) {
        RenewalResponse response = renewalService.getRenewalById(renewalId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milestone-renewal")
    public ResponseEntity<List<RenewalResponse>> getRenewalsByMilestoneId(
            @RequestParam Long userid,
            @RequestParam Long milestoneId) {

        List<RenewalResponse> responses = renewalService.getRenewalsByMilestoneId(userid, milestoneId);
        return ResponseEntity.ok(responses);
    }



    @GetMapping("/all-milestones")
    public ResponseEntity<List<RenewalResponse>> getAllMileStone(@RequestParam Long userid) {

        List<RenewalResponse> responses = renewalService.fetchAllRenewals(userid);
        return ResponseEntity.ok(responses);
    }




}


