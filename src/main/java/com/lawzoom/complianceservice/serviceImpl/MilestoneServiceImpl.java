package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequestForFetch;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
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
        // Step 1: Validate Compliance
        Compliance compliance = complianceRepository.findById(milestoneRequest.getComplianceId())
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + milestoneRequest.getComplianceId()));

        // Step 2: Validate Business Unit
        BusinessUnit businessUnit = businessUnitRepository.findById(milestoneRequest.getBusinessUnitId())
                .orElseThrow(() -> new NotFoundException("Business Unit not found with ID: " + milestoneRequest.getBusinessUnitId()));

        // Step 3: Validate Reporter
        User reporter = userRepository.findById(milestoneRequest.getReporterId())
                .orElseThrow(() -> new NotFoundException("Reporter not found with ID: " + milestoneRequest.getReporterId()));

        // Step 4: Validate Assigned To User
        User assignedToUser = null;
        if (milestoneRequest.getAssignedTo() != null) {
            assignedToUser = userRepository.findById(milestoneRequest.getAssignedTo())
                    .orElseThrow(() -> new NotFoundException("Assigned To user not found with ID: " + milestoneRequest.getAssignedTo()));
        }

        // Step 5: Validate Assigned By User
        User assignedByUser = null;
        if (milestoneRequest.getAssignedBy() != null) {
            assignedByUser = userRepository.findById(milestoneRequest.getAssignedBy())
                    .orElseThrow(() -> new NotFoundException("Assigned By user not found with ID: " + milestoneRequest.getAssignedBy()));
        }

        // Step 6: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(milestoneRequest.getSubscriberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscriberId: " + milestoneRequest.getSubscriberId()));

        // Step 7: Validate Whom to Send Reminder
        User whomToSend = null;
        if (milestoneRequest.getWhomToSend() != null) {
            whomToSend = userRepository.findById(milestoneRequest.getWhomToSend())
                    .orElseGet(() -> userRepository.findSuperAdminBySubscriberId(subscriber.getId()));
        }

        // Step 8: Create Milestone
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

        // Step 9: Create Reminder
        Reminder reminder = new Reminder();
        reminder.setMilestone(milestone);
        reminder.setSubscriber(subscriber);
        reminder.setSuperAdmin(userRepository.findSuperAdminBySubscriberId(subscriber.getId())); // Default to Super Admin
        reminder.setCreatedBy(reporter);
        reminder.setWhomToSend(whomToSend);
        reminder.setReminderDate(milestoneRequest.getReminderDate());
        reminder.setReminderEndDate(milestoneRequest.getReminderEndDate());
        reminder.setNotificationTimelineValue(milestoneRequest.getNotificationTimelineValue());
        reminder.setRepeatTimelineValue(milestoneRequest.getRepeatTimelineValue());
        reminder.setRepeatTimelineType(milestoneRequest.getRepeatTimelineType());
        milestone.getReminders().add(reminder);

        MileStone savedMilestone = milestoneRepository.save(milestone);

        // Step 10: Prepare Response
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

        List<MileStone> milestones;

        if (isSuperAdmin(request.getUserId())) {
            // Case 1: User is SUPER_ADMIN
            milestones = milestoneRepository.findMilestonesBySubscriber(subscriber.getId());
        } else {
            // Case 2: User is USER
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId()));

            milestones = milestoneRepository.findMilestonesBySubscriberAndAssignedTo(subscriber.getId(), user.getId());
        }

        // Map milestones to response DTOs
        return milestones.stream()
                .map(this::mapToMilestoneResponse)
                .toList();
    }

    // Utility method to determine if a user is a SUPER_ADMIN
    private boolean isSuperAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        // Check if the user has a role with the name "SUPER_ADMIN"
        return user.getRoles().stream()
                .anyMatch(role -> "SUPER_ADMIN".equalsIgnoreCase(role.getRoleName()));
    }


    // Utility method to map MileStone entity to MilestoneResponse DTO
    private MilestoneResponse mapToMilestoneResponse(MileStone milestone) {
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
        response.setReporterName(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getUserName() : null);
        response.setAssignedTo(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getId() : null);
        response.setAssignedName(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getUserName() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
        response.setAssigneeMail(milestone.getAssigneeMail());
        response.setIssuedDate(milestone.getIssuedDate());
        response.setCriticality(milestone.getCriticality());
        response.setBusinessUnitId(milestone.getBusinessUnit().getId());
        return response;
    }

    @Override
    public List<MilestoneResponse> fetchMilestonesByStatus(Long userId, Long subscriberId, String status) {
        // Step 1: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Step 2: Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        // Step 3: Check User Role
        boolean isSuperAdmin = user.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));

        List<MileStone> milestones;

        if (isSuperAdmin) {
            // If user is SUPER ADMIN, fetch all milestones for the subscriber
            milestones = milestoneRepository.findBySubscriber(subscriber);
        } else {
            // If user is not SUPER ADMIN, fetch milestones assigned to the user with the given status
            milestones = milestoneRepository.findBySubscriberAndAssignedToAndStatus(subscriber, user, status);
        }

        // Step 4: Map milestones to response
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
            response.setReporterName(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getUserName() : null);
            response.setAssignedTo(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getId() : null);
            response.setAssignedName(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getUserName() : null);
            response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
            response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
            response.setAssigneeMail(milestone.getAssigneeMail());
            response.setIssuedDate(milestone.getIssuedDate());
            response.setCriticality(milestone.getCriticality());
            response.setBusinessUnitId(milestone.getBusinessUnit().getId());
            response.setSubscriberId(milestone.getSubscriber().getId());
            response.setRemark(milestone.getRemark());
            responses.add(response);
        }

        return responses;
    }


}

