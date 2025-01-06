package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.RenewalRequest;
import com.lawzoom.complianceservice.dto.RenewalResponse;
import jakarta.validation.Valid;

public interface RenewalService {
    RenewalResponse createOrUpdateRenewal(Long complianceId, RenewalRequest request);
    RenewalResponse getRenewalByComplianceId(Long complianceId);
    void deleteRenewal(Long complianceId);

    RenewalResponse createRenewalForCompliance(Long complianceId, @Valid RenewalRequest renewalRequest);
}
