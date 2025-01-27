package com.lawzoom.complianceservice.service.dashboardService;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DashboardService {
    ResponseEntity<Map<String, Object>> getCompanyDetails(Long userId, Long subscriberId);

    ResponseEntity<Map<String, Object>> getGstDetailsCount(Long subscriberId, Long companyId);

    ResponseEntity<Map<String, Object>> getBusinessUnitCount(Long subscriberId, Long companyId);


    ResponseEntity<Map<String, Object>> getUserCount(Long subscriberId);
}
