package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequestForFetch;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.MileStone;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private ComplianceRepo complianceRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public MilestoneResponse createMilestone(MilestoneRequest milestoneRequest) {
        // Validate Compliance
        Compliance compliance = complianceRepository.findById(milestoneRequest.getComplianceId())
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + milestoneRequest.getComplianceId()));

        // Validate Business Unit
        BusinessUnit businessUnit = businessUnitRepository.findById(milestoneRequest.getBusinessUnitId())
                .orElseThrow(() -> new NotFoundException("Business Unit not found with ID: " + milestoneRequest.getBusinessUnitId()));

        // Validate Reporter
        User reporter = userRepository.findById(milestoneRequest.getReporterId())
                .orElseThrow(() -> new NotFoundException("Reporter not found with ID: " + milestoneRequest.getReporterId()));

        // Validate Assigned To User
        User assignedToUser = null;
        if (milestoneRequest.getAssignedTo() != null) {
            assignedToUser = userRepository.findById(milestoneRequest.getAssignedTo())
                    .orElseThrow(() -> new NotFoundException("Assigned To user not found with ID: " + milestoneRequest.getAssignedTo()));
        }

        // Validate Assigned By User
        User assignedByUser = null;
        if (milestoneRequest.getAssignedBy() != null) {
            assignedByUser = userRepository.findById(milestoneRequest.getAssignedBy())
                    .orElseThrow(() -> new NotFoundException("Assigned By user not found with ID: " + milestoneRequest.getAssignedBy()));
        }

        Subscriber subscriber = subscriberRepository.findById(milestoneRequest.getSubscriberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscriberId: " + milestoneRequest.getSubscriberId()));

        // Create Milestone Entity
        MileStone milestone = new MileStone();
        milestone.setMileStoneName(milestoneRequest.getMileStoneName());
        milestone.setDescription(milestoneRequest.getDescription());
        milestone.setCompliance(compliance);
        milestone.setBusinessUnit(businessUnit);
        milestone.setTaskReporter(reporter);
        milestone.setAssignedTo(assignedToUser);
        milestone.setAssignedBy(assignedByUser);
        milestone.setAssigneeMail(milestoneRequest.getAssigneeMail());
        milestone.setIssuedDate(milestoneRequest.getIssuedDate());
        milestone.setCriticality(milestoneRequest.getCriticality());
        milestone.setStatus(milestoneRequest.getStatus() != null ? milestoneRequest.getStatus() : "Pending");
        milestone.setCreatedAt(new Date());
        milestone.setUpdatedAt(new Date());
        milestone.setSubscriber(subscriber);
        milestone.setRemark(milestoneRequest.getRemark());

        MileStone savedMilestone = milestoneRepository.save(milestone);

        // Prepare Response DTO
        MilestoneResponse milestoneResponse = new MilestoneResponse();
        milestoneResponse.setId(savedMilestone.getId());
        milestoneResponse.setMileStoneName(savedMilestone.getMileStoneName());
        milestoneResponse.setDescription(savedMilestone.getDescription());
        milestoneResponse.setStatus(savedMilestone.getStatus());
        milestoneResponse.setCreatedAt(savedMilestone.getCreatedAt());
        milestoneResponse.setUpdatedAt(savedMilestone.getUpdatedAt());
        milestoneResponse.setEnable(savedMilestone.isEnable());
        milestoneResponse.setComplianceId(savedMilestone.getCompliance().getId());
        milestoneResponse.setReporterId(reporter.getId());
        milestoneResponse.setRemark(savedMilestone.getRemark());
        if (assignedToUser != null) {
            milestoneResponse.setAssignedTo(assignedToUser.getId());
        }
        if (assignedByUser != null) {
            milestoneResponse.setAssignedBy(assignedByUser.getId());
        }
        milestoneResponse.setAssigneeMail(savedMilestone.getAssigneeMail());
        milestoneResponse.setIssuedDate(savedMilestone.getIssuedDate());
        milestoneResponse.setCriticality(savedMilestone.getCriticality());
        milestoneResponse.setBusinessUnitId(businessUnit.getId());
        milestoneResponse.setSubscriberId(savedMilestone.getSubscriber().getId());

        return milestoneResponse;
    }

    @Override
    public MilestoneResponse fetchMilestoneById(Long milestoneId) {
        // Step 1: Validate Milestone ID
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Step 2: Manually Map to Response DTO
        MilestoneResponse response = new MilestoneResponse();
        response.setId(milestone.getId());
        response.setMileStoneName(milestone.getMileStoneName());
        response.setDescription(milestone.getDescription());
        response.setStatus(milestone.getStatus());
        response.setCreatedAt(milestone.getCreatedAt());
        response.setUpdatedAt(milestone.getUpdatedAt());
        response.setEnable(milestone.isEnable());
        response.setComplianceId(milestone.getCompliance().getId());
        response.setReporterId(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getId() : null);
        response.setAssignedTo(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getId() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssigneeMail(milestone.getAssigneeMail());
        response.setIssuedDate(milestone.getIssuedDate());
        response.setCriticality(milestone.getCriticality());
        response.setBusinessUnitId(milestone.getBusinessUnit().getId());

        return response;
    }


    @Override
    public List<MilestoneResponse> fetchAllMilestones(MilestoneRequestForFetch request) {
        // Step 1: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(request.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + request.getSubscriberId()));

        // Step 2: Validate Business Unit
        BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
                .orElseThrow(() -> new NotFoundException("Business Unit not found with ID: " + request.getBusinessUnitId()));

        // Step 3: Validate Compliance
        Compliance compliance = complianceRepository.findById(request.getComplianceId())
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + request.getComplianceId()));

        // Step 4: Fetch Milestones using Native Query
        List<MileStone> milestones = milestoneRepository.findMilestonesByParameters(
                compliance.getId(),
                businessUnit.getId(),
                subscriber.getId()
        );

        // Step 5: Map Milestones to Response DTOs
        List<MilestoneResponse> responses = new ArrayList<>();
        for (MileStone milestone : milestones) {
            MilestoneResponse response = new MilestoneResponse();
            response.setId(milestone.getId());
            response.setMileStoneName(milestone.getMileStoneName());
            response.setDescription(milestone.getDescription());
            response.setStatus(milestone.getStatus());
            response.setCreatedAt(milestone.getCreatedAt());
            response.setUpdatedAt(milestone.getUpdatedAt());
            response.setEnable(milestone.isEnable());
            response.setComplianceId(milestone.getCompliance().getId());
            response.setReporterId(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getId() : null);
            response.setReporterName(milestone.getTaskReporter().getUserName());
            response.setAssignedTo(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getId() : null);
            response.setAssignedName(milestone.getAssignedTo().getUserName());
            response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
            response.setAssignedByName(milestone.getAssignedBy().getUserName());
            response.setAssigneeMail(milestone.getAssigneeMail());
            response.setIssuedDate(milestone.getIssuedDate());
            response.setCriticality(milestone.getCriticality());
            response.setBusinessUnitId(milestone.getBusinessUnit().getId());
            responses.add(response);
        }

        return responses;
    }
}

