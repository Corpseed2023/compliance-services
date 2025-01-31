package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface RenewalService {

    List<RenewalResponse> getRenewalsByMilestoneId(Long userid, Long milestoneId);

    RenewalResponse getRenewalById(Long renewalId);


    List<RenewalResponse> fetchAllRenewals(Long userid);

    MilestoneRenewalResponse createOrUpdateMilestoneRenewal(Long milestoneId, RenewalRequest request, Long renewalId);


}
