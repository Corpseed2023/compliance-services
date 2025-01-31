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
    @GetMapping("/get-company-details")
    public ResponseEntity<Map<String, Object>> getCompanyDetails(
            @RequestParam Long userId,
            @RequestParam Long subscriberId) {
        return dashboardService.getCompanyDetails(userId, subscriberId);
    }

    /**
     * Fetch GST details count for a subscriber or a company.
     *
     * @param subscriberId the ID of the subscriber
     * @param companyId    the ID of the company (optional)
     * @return a map containing the count of GST details
     */
    @GetMapping("/gst-details-count")
    public ResponseEntity<Map<String, Object>> getGstDetailsCount(
            @RequestParam Long subscriberId,
            @RequestParam(required = false) Long companyId) {
        return dashboardService.getGstDetailsCount(subscriberId, companyId);
    }


    /**
     * Fetch the count of business units for a subscriber or a company.
     *
     * @param subscriberId the ID of the subscriber
     * @param companyId    the ID of the company (optional)
     * @return a map containing the count of business units
     */
    @GetMapping("/business-unit-count")
    public ResponseEntity<Map<String, Object>> getBusinessUnitCount(
            @RequestParam Long subscriberId,
            @RequestParam(required = false) Long companyId) {
        return dashboardService.getBusinessUnitCount(subscriberId, companyId);
    }

    /**
     * Fetch user count for a subscriber or a company.
     *
     * @param subscriberId the ID of the subscriber
     * @param companyId    the ID of the company (optional)
     * @return a map containing the user count
     */
    @GetMapping("/user-count")
    public ResponseEntity<Map<String, Object>> getUserCount(
            @RequestParam Long subscriberId){
        return dashboardService.getUserCount(subscriberId);
    }


}
