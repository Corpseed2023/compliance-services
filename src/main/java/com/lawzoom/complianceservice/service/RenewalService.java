package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface RenewalService {


    MilestoneRenewalResponse createMilestoneRenewal(Long milestoneId, RenewalRequest renewalRequest);


    List<RenewalResponse> getRenewalsByMilestoneId(Long userid, Long milestoneId);

    RenewalResponse getRenewalById(Long renewalId);

    MilestoneRenewalResponse updateMilestoneRenewal(Long renewalId, RenewalRequest renewalRequest, Long mileStoneId);

    List<RenewalResponse> fetchAllRenewals(Long userid);
}
