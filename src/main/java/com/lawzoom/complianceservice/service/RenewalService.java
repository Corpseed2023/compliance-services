package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import jakarta.validation.Valid;

public interface RenewalService {


    MilestoneRenewalResponse createMilestoneRenewal(Long milestoneId, RenewalRequest renewalRequest);

    MilestoneRenewalResponse updateMilestoneRenewal(Long renewalId, RenewalRequest renewalRequest);
}
