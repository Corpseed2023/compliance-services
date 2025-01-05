package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.RenewalRequest;
import com.lawzoom.complianceservice.dto.RenewalResponse;

public interface RenewalService {
    RenewalResponse createOrUpdateRenewal(Long complianceId, RenewalRequest request);
    RenewalResponse getRenewalByComplianceId(Long complianceId);
    void deleteRenewal(Long complianceId);
}
