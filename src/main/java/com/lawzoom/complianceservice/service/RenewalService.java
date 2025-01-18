package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import jakarta.validation.Valid;

public interface RenewalService {
    RenewalResponse generateComplianceRenewal(Long complianceId, RenewalRequest request);
    RenewalResponse getRenewalByComplianceId(Long complianceId);
    void deleteRenewal(Long complianceId);

    RenewalResponse createRenewalForCompliance(Long complianceId, @Valid RenewalRequest renewalRequest);

    MilestoneRenewalResponse createMilestoneRenewal(Long milestoneId, RenewalRequest renewalRequest);
}
