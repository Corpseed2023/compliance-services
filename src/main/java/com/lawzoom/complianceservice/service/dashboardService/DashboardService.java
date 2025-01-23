package com.lawzoom.complianceservice.service.dashboardService;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DashboardService {
    ResponseEntity<Map<String, Object>> getDashboardDetails(Long userId, Long subscriberId);
}
