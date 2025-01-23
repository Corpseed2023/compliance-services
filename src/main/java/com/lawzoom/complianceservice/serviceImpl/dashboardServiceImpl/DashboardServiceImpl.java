package com.lawzoom.complianceservice.serviceImpl.dashboardServiceImpl;

import com.lawzoom.complianceservice.repository.GstDetailsRepository;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.service.dashboardService.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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






    @Override
    public ResponseEntity<Map<String, Object>> getDashboardDetails(Long userId, Long subscriberId) {
        return null;
    }
}
