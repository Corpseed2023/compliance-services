package com.lawzoom.complianceservice.serviceImpl.dashboardServiceImpl;

import com.lawzoom.complianceservice.repository.GstDetailsRepository;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.service.dashboardService.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private GstDetailsRepository gstDetailsRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;
    @Autowired

    private ComplianceRepo complianceRepo;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getCompanyDetails(Long userId, Long subscriberId) {
        Map<String, Object> response = new HashMap<>();

        // Validate user existence
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Error: User not found!"));

        // Fetch the count of companies under the subscriber
        long companyCount = companyRepository.countBySubscriberId(subscriberId);

        // Add the company count to the response
        response.put("companyCount", companyCount);
        response.put("status", "success");
        response.put("message", "Company details fetched successfully.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getGstDetailsCount(Long subscriberId, Long companyId) {
        Map<String, Object> response = new HashMap<>();

        if (companyId != null) {
            // Count GST details for a specific company
            long gstCount = gstDetailsRepository.countByCompanyId(companyId);
            response.put("gstDetailsCount", gstCount);
            response.put("message", "GST details count for company fetched successfully.");
        } else {
            // Count GST details for all companies under a subscriber
            long gstCount = gstDetailsRepository.countBySubscriberId(subscriberId);
            response.put("gstDetailsCount", gstCount);
            response.put("message", "GST details count for subscriber fetched successfully.");
        }

        response.put("status", "success");
        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<Map<String, Object>> getBusinessUnitCount(Long subscriberId, Long companyId) {
        Map<String, Object> response = new HashMap<>();

        if (companyId != null) {
            // Count business units for a specific company
            long businessUnitCount = businessUnitRepository.countByCompanyId(companyId);
            response.put("businessUnitCount", businessUnitCount);
            response.put("message", "Business unit count for company fetched successfully.");
        } else {
            // Count business units for all companies under the subscriber
            long businessUnitCount = businessUnitRepository.countBySubscriberId(subscriberId);
            response.put("businessUnitCount", businessUnitCount);
            response.put("message", "Business unit count for subscriber fetched successfully.");
        }

        response.put("status", "success");
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getUserCount(Long subscriberId) {
        Map<String, Object> response = new HashMap<>();

        if (subscriberId == null) {
            throw new IllegalArgumentException("Subscriber ID cannot be null.");
        }

        // Fetch user count by subscriber
        long userCount = userRepository.countBySubscriberId(subscriberId);

        response.put("userCount", userCount);
        response.put("message", "User count for subscriber fetched successfully.");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }





}
