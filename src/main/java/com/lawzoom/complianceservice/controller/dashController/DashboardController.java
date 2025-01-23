package com.lawzoom.complianceservice.controller.dashController;

import com.lawzoom.complianceservice.service.dashboardService.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Fetch all dashboard details for a specific user and subscriber.
     *
     * @param userId       the ID of the user
     * @param subscriberId the ID of the subscriber
     * @return a map containing dashboard details
     */
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getDashboardDetails(
            @RequestParam Long userId,
            @RequestParam Long subscriberId) {
        return dashboardService.getDashboardDetails(userId, subscriberId);
    }
}
