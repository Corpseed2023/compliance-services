package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.DocumentRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequestForFetch;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public ResponseEntity<Map<String, Object>> createMilestone(MilestoneRequest milestoneRequest) {
        // Step 1: Validate Compliance
        Compliance compliance = complianceRepository.findById(milestoneRequest.getComplianceId())
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + milestoneRequest.getComplianceId()));

        // Step 2: Validate Business Unit
        BusinessUnit businessUnit = businessUnitRepository.findById(milestoneRequest.getBusinessUnitId())
                .orElseThrow(() -> new NotFoundException("Business Unit not found with ID: " + milestoneRequest.getBusinessUnitId()));

        // Step 3: Validate Reporter
        User reporter = userRepository.findById(milestoneRequest.getManagerId())
                .orElseThrow(() -> new NotFoundException("Reporter not found with ID: " + milestoneRequest.getManagerId()));

        // Step 4: Validate Assigned To User
        User assignedToUser = null;
        if (milestoneRequest.getAssignee() != null) {
            assignedToUser = userRepository.findById(milestoneRequest.getAssignee())
                    .orElseThrow(() -> new NotFoundException("Assigned To user not found with ID: " + milestoneRequest.getAssignee()));
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

        // Step 7: Fetch Status
        Status status = statusRepository.findById(milestoneRequest.getStatus())
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + milestoneRequest.getStatus()));

        // Step 8: Check WorkStatus
        if (milestoneRequest.getWorkStatus() == 1) { // Not Done Yet
            if (milestoneRequest.getMileStoneName() == null || milestoneRequest.getDescription() == null ||
                    milestoneRequest.getStartedDate() == null || milestoneRequest.getDueDate() == null) {
                throw new IllegalArgumentException("Milestone Name, Description, Start Date, and Due Date are mandatory for pending compliance.");
            }

            // Create Milestone
            MileStone milestone = new MileStone();
            milestone.setMileStoneName(milestoneRequest.getMileStoneName());
            milestone.setDescription(milestoneRequest.getDescription());
            milestone.setStartedDate(milestoneRequest.getStartedDate());
            milestone.setDueDate(milestoneRequest.getDueDate());
            milestone.setCompliance(compliance);
            milestone.setBusinessUnit(businessUnit);
            milestone.setManager(reporter);
            milestone.setAssigned(assignedToUser);
            milestone.setAssignedBy(assignedByUser);
            milestone.setCriticality(milestoneRequest.getCriticality());
            milestone.setStatus(status);
            milestone.setSubscriber(subscriber);
            milestone.setRemark(milestoneRequest.getRemark());

            // Add Reminder (Optional)
            if (milestoneRequest.getReminderDate() != null) {
                Reminder reminder = new Reminder();
                reminder.setMilestone(milestone);
                reminder.setCompliance(compliance);
                reminder.setSubscriber(subscriber);
                reminder.setCreatedBy(reporter);
                reminder.setWhomToSend(reporter); // Assuming whomToSend is reporter by default
                reminder.setReminderDate(milestoneRequest.getReminderDate());
                reminder.setReminderEndDate(milestoneRequest.getReminderEndDate());
                reminder.setNotificationTimelineValue(milestoneRequest.getNotificationTimelineValue());
                reminder.setRepeatTimelineValue(milestoneRequest.getRepeatTimelineValue());
                reminder.setRepeatTimelineType(milestoneRequest.getRepeatTimelineType());
                milestone.getReminders().add(reminder);
            }

            // Add Documents (Optional)
            if (milestoneRequest.getDocuments() != null && !milestoneRequest.getDocuments().isEmpty()) {
                for (DocumentRequest documentRequest : milestoneRequest.getDocuments()) {
                    Document document = new Document();
                    document.setDocumentName(documentRequest.getDocumentName());
                    document.setFileName(documentRequest.getFileName());
                    document.setIssueDate(documentRequest.getIssueDate());
                    document.setReferenceNumber(documentRequest.getReferenceNumber());
                    document.setRemarks(documentRequest.getRemarks());
                    User addedBy = userRepository.findById(documentRequest.getAddedById())
                            .orElseThrow(() -> new NotFoundException("Added By user not found with ID: " + documentRequest.getAddedById()));
                    document.setAddedBy(addedBy);
                    document.setMilestone(milestone);
                    document.setSubscriber(subscriber);
                    milestone.getDocuments().add(document);
                }
            }

            // Save Milestone
            milestoneRepository.save(milestone);

            // Prepare Response
            Map<String, Object> response = new HashMap<>();
            response.put("id", milestone.getId());
            response.put("mileStoneName", milestone.getMileStoneName());
            response.put("description", milestone.getDescription());
            response.put("status", milestone.getStatus().getName());
            response.put("createdAt", milestone.getCreatedAt());
            response.put("updatedAt", milestone.getUpdatedAt());
            response.put("isEnable", milestone.isEnable());
            response.put("complianceId", milestone.getCompliance().getId());
            response.put("reporterId", reporter.getId());
            response.put("remark", milestone.getRemark());

            return ResponseEntity.status(201).body(response);
        }

        // Handle other work statuses (e.g., Completed, Not Applicable)
        throw new IllegalArgumentException("Invalid or unsupported work status for this operation.");
    }



    @Override
    public MilestoneResponse fetchMilestoneById(Long milestoneId) {
        // Step 1: Validate Milestone ID
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Step 2: Map Milestone Entity to Response DTO
        MilestoneResponse response = new MilestoneResponse();
        response.setId(milestone.getId());
        response.setMileStoneName(milestone.getMileStoneName());
        response.setDescription(milestone.getDescription());
        response.setStatusId(milestone.getStatus() != null ? milestone.getStatus().getId() : null);
        response.setStatus(milestone.getStatus() != null ? milestone.getStatus().getName() : null);
        response.setCreatedAt(milestone.getCreatedAt());
        response.setUpdatedAt(milestone.getUpdatedAt());
        response.setEnable(milestone.isEnable());
        response.setComplianceId(milestone.getCompliance() != null ? milestone.getCompliance().getId() : null);
        response.setManagerId(milestone.getManager() != null ? milestone.getManager().getId() : null);
        response.setReporterName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
        response.setAssigned(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
        response.setAssignedName(milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
        response.setIssuedDate(milestone.getIssuedDate());
        response.setCriticality(milestone.getCriticality());
        response.setRemark(milestone.getRemark());
        response.setBusinessUnitId(milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getId() : null);
        response.setSubscriberId(milestone.getSubscriber() != null ? milestone.getSubscriber().getId() : null);

        // Step 3: Map Reminder Details to ReminderDetails DTO
        List<MilestoneResponse.ReminderDetails> reminderDetails = milestone.getReminders().stream().map(reminder -> {
            MilestoneResponse.ReminderDetails remDetails = new MilestoneResponse.ReminderDetails();
            remDetails.setId(reminder.getId());
            remDetails.setReminderDate(reminder.getReminderDate());
            remDetails.setReminderEndDate(reminder.getReminderEndDate());
            remDetails.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
            remDetails.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
            remDetails.setRepeatTimelineType(reminder.getRepeatTimelineType());
            remDetails.setWhomToSendId(reminder.getWhomToSend() != null ? reminder.getWhomToSend().getId() : null);
            remDetails.setWhomToSendName(reminder.getWhomToSend() != null ? reminder.getWhomToSend().getUserName() : null);
            return remDetails;
        }).toList();
        response.setReminders(reminderDetails);

        // Step 4: Map Renewal Details to RenewalDetails DTO
        List<MilestoneResponse.RenewalDetails> renewalDetails = milestone.getRenewals().stream().map(renewal -> {
            MilestoneResponse.RenewalDetails renDetails = new MilestoneResponse.RenewalDetails();
            renDetails.setId(renewal.getId());
            renDetails.setNextRenewalDate(renewal.getNextRenewalDate());
            renDetails.setRenewalFrequency(renewal.getRenewalFrequency());
            renDetails.setRenewalType(renewal.getRenewalType());
            renDetails.setRenewalNotes(renewal.getRenewalNotes());
            return renDetails;
        }).toList();
        response.setRenewals(renewalDetails);

        // Step 5: Map Document Details to DocumentDetails DTO
        List<MilestoneResponse.DocumentDetails> documentDetails = milestone.getDocuments().stream().map(document -> {
            MilestoneResponse.DocumentDetails docDetails = new MilestoneResponse.DocumentDetails();
            docDetails.setId(document.getId());
            docDetails.setDocumentName(document.getDocumentName());
            docDetails.setFileName(document.getFileName());
            docDetails.setIssueDate(document.getIssueDate());
            docDetails.setReferenceNumber(document.getReferenceNumber());
            docDetails.setRemarks(document.getRemarks());
            docDetails.setUploadDate(document.getUploadDate());
            return docDetails;
        }).toList();
        response.setDocuments(documentDetails);

        return response;
    }



    @Override
    public List<MilestoneResponse> fetchAllMilestones(MilestoneRequestForFetch request) {
        // Step 1: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(request.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + request.getSubscriberId()));

        // Step 2: Fetch Milestones
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

        // Step 3: Map milestones to response DTOs
        return milestones.stream()
                .map(this::mapToMilestoneResponseWithDetails)
                .toList();
    }

    private MilestoneResponse mapToMilestoneResponseWithDetails(MileStone milestone) {
        MilestoneResponse response = new MilestoneResponse();

        // Set basic details
        response.setId(milestone.getId());
        response.setMileStoneName(milestone.getMileStoneName());
        response.setDescription(milestone.getDescription());
        response.setStatusId(milestone.getStatus() != null ? milestone.getStatus().getId() : null);
        response.setStatus(milestone.getStatus() != null ? milestone.getStatus().getName() : null);
        response.setCreatedAt(milestone.getCreatedAt());
        response.setUpdatedAt(milestone.getUpdatedAt());
        response.setEnable(milestone.isEnable());
        response.setComplianceId(milestone.getCompliance() != null ? milestone.getCompliance().getId() : null);
        response.setManagerId(milestone.getManager() != null ? milestone.getManager().getId() : null);
        response.setReporterName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
        response.setAssigned(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
        response.setAssignedName(milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
        response.setIssuedDate(milestone.getIssuedDate());
        response.setCriticality(milestone.getCriticality());
        response.setRemark(milestone.getRemark());
        response.setBusinessUnitId(milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getId() : null);
        response.setSubscriberId(milestone.getSubscriber() != null ? milestone.getSubscriber().getId() : null);
        response.setStartedDate(milestone.getStartedDate());
        response.setDueDate(milestone.getDueDate());
        response.setCompletedDate(milestone.getCompletedDate());


        // Map Reminder details
        List<MilestoneResponse.ReminderDetails> reminderDetails = milestone.getReminders().stream()
                .map(reminder -> {
                    MilestoneResponse.ReminderDetails rd = new MilestoneResponse.ReminderDetails();
                    rd.setId(reminder.getId());
                    rd.setReminderDate(reminder.getReminderDate());
                    rd.setReminderEndDate(reminder.getReminderEndDate());
                    rd.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
                    rd.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
                    rd.setRepeatTimelineType(reminder.getRepeatTimelineType());
                    rd.setWhomToSendId(reminder.getWhomToSend() != null ? reminder.getWhomToSend().getId() : null);
                    rd.setWhomToSendName(reminder.getWhomToSend() != null ? reminder.getWhomToSend().getUserName() : null);
                    return rd;
                })
                .toList();
        response.setReminders(reminderDetails);

        // Map Renewal details
        List<MilestoneResponse.RenewalDetails> renewalDetails = milestone.getRenewals().stream()
                .map(renewal -> {
                    MilestoneResponse.RenewalDetails rd = new MilestoneResponse.RenewalDetails();
                    rd.setId(renewal.getId());
                    rd.setNextRenewalDate(renewal.getNextRenewalDate());
                    rd.setRenewalFrequency(renewal.getRenewalFrequency());
                    rd.setRenewalType(renewal.getRenewalType());
                    rd.setRenewalNotes(renewal.getRenewalNotes());
                    return rd;
                })
                .toList();
        response.setRenewals(renewalDetails);

        // Map Document details
        List<MilestoneResponse.DocumentDetails> documentDetails = milestone.getDocuments().stream()
                .map(document -> {
                    MilestoneResponse.DocumentDetails dd = new MilestoneResponse.DocumentDetails();
                    dd.setId(document.getId());
                    dd.setDocumentName(document.getDocumentName());
                    dd.setFileName(document.getFileName());
                    dd.setIssueDate(document.getIssueDate());
                    dd.setReferenceNumber(document.getReferenceNumber());
                    dd.setRemarks(document.getRemarks());
                    dd.setUploadDate(document.getUploadDate());
                    return dd;
                })
                .toList();
        response.setDocuments(documentDetails);

        return response;
    }



    // Utility method to determine if a user is a SUPER_ADMIN
    private boolean isSuperAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        // Check if the user has a role with the name "SUPER_ADMIN"
        return user.getRoles().stream()
                .anyMatch(role -> "SUPER_ADMIN".equalsIgnoreCase(role.getRoleName()));
    }


    @Override
    public List<MilestoneResponse> fetchMilestonesByStatus(Long userId, Long subscriberId, String statusName) {
        // Step 1: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Step 2: Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        // Step 3: Fetch the Status entity
        Status status = statusRepository.findByName(statusName)
                .orElseThrow(() -> new NotFoundException("Status not found with name: " + statusName));

        // Step 4: Check User Role
        boolean isSuperAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));

        List<MileStone> milestones;

        if (isSuperAdmin) {
            // If user is SUPER ADMIN, fetch all milestones for the subscriber
            milestones = milestoneRepository.findBySubscriber(subscriber);
        } else {
            // If user is not SUPER ADMIN, fetch milestones assigned to the user with the given status
            milestones = milestoneRepository.findBySubscriberAndAssignedAndStatus(subscriber, user, status);
        }

        // Step 5: Map milestones to response
        List<MilestoneResponse> responses = new ArrayList<>();
        for (MileStone milestone : milestones) {
            MilestoneResponse response = new MilestoneResponse();
            response.setId(milestone.getId());
            response.setMileStoneName(milestone.getMileStoneName());
            response.setDescription(milestone.getDescription());
            response.setStatus(milestone.getStatus().getName()); // Use status name
            response.setCreatedAt(milestone.getCreatedAt());
            response.setUpdatedAt(milestone.getUpdatedAt());
            response.setEnable(milestone.isEnable());
            response.setComplianceId(milestone.getCompliance().getId());
            response.setManagerId(milestone.getManager() != null ? milestone.getManager().getId() : null);
            response.setReporterName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
            response.setAssigned(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
            response.setAssignedName(milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
            response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
            response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
            response.setIssuedDate(milestone.getIssuedDate());
            response.setCriticality(milestone.getCriticality());
            response.setBusinessUnitId(milestone.getBusinessUnit().getId());
            response.setSubscriberId(milestone.getSubscriber().getId());
            response.setRemark(milestone.getRemark());
            responses.add(response);
        }

        return responses;
    }


    @Override
    public MilestoneResponse updateMilestoneAssignment(Long milestoneId, Long assignedToId, Long taskReporterId) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Validate Assigned To User
        User assignedTo = userRepository.findById(assignedToId)
                .orElseThrow(() -> new NotFoundException("Assigned To user not found with ID: " + assignedToId));

        // Validate Task Reporter
        User mileStoneManager = userRepository.findById(taskReporterId)
                .orElseThrow(() -> new NotFoundException("Task Reporter not found with ID: " + taskReporterId));

        // Update Fields
        milestone.setAssigned(assignedTo);
        milestone.setManager(mileStoneManager);
        milestone.setUpdatedAt(new Date());

        // Save Updated Milestone
        MileStone updatedMilestone = milestoneRepository.save(milestone);

        // Map to Response DTO
        return mapToMilestoneResponseWithDetails(updatedMilestone);
    }


    @Override
    public MilestoneResponse updateMilestoneStatus(Long milestoneId, Long statusId) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Validate Status
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + statusId));

        // Update Milestone Status
        milestone.setStatus(status);
        milestone.setUpdatedAt(new Date());

        // Save Updated Milestone
        MileStone updatedMilestone = milestoneRepository.save(milestone);

        // Map Updated Milestone to Response
        return mapToMilestoneResponseWithDetails(updatedMilestone);
    }


}

