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
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
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

        // Step 7: Fetch Default Status
        Status defaultStatus = statusRepository.findByName("INITIATED")
                .orElseThrow(() -> new NotFoundException("Status 'INITIATED' not found"));

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
        milestone.setStatus(defaultStatus); // Set default status from Status table
        milestone.setCreatedAt(new Date());
        milestone.setUpdatedAt(new Date());
        milestone.setSubscriber(subscriber);
        milestone.setRemark(milestoneRequest.getRemark());

        // Step 9: Add Documents if Provided
        if (milestoneRequest.getDocuments() != null && !milestoneRequest.getDocuments().isEmpty()) {
            List<Document> documents = new ArrayList<>();
            for (DocumentRequest documentRequest : milestoneRequest.getDocuments()) {
                Document document = new Document();
                document.setDocumentName(documentRequest.getDocumentName());
                document.setFileName(documentRequest.getFileName());
                document.setIssueDate(documentRequest.getIssueDate());
                document.setReferenceNumber(documentRequest.getReferenceNumber());
                document.setRemarks(documentRequest.getRemarks());
                document.setUploadDate(new Date());
                document.setMilestone(milestone); // Link document to milestone
                document.setSubscriber(subscriber);
                document.setAddedBy(reporter);
                documents.add(document);
            }
            milestone.getDocuments().addAll(documents);
        }

        // Save Milestone
        MileStone savedMilestone = milestoneRepository.save(milestone);

        // Step 10: Prepare Response
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedMilestone.getId());
        response.put("mileStoneName", savedMilestone.getMileStoneName());
        response.put("description", savedMilestone.getDescription());
        response.put("status", savedMilestone.getStatus().getName());
        response.put("createdAt", savedMilestone.getCreatedAt());
        response.put("updatedAt", savedMilestone.getUpdatedAt());
        response.put("isEnable", savedMilestone.isEnable());
        response.put("complianceId", savedMilestone.getCompliance().getId());
        response.put("reporterId", reporter.getId());
        response.put("remark", savedMilestone.getRemark());

        // Prepare Document Details
        List<Map<String, Object>> documentDetails = new ArrayList<>();
        for (Document document : savedMilestone.getDocuments()) {
            Map<String, Object> docMap = new HashMap<>();
            docMap.put("documentName", document.getDocumentName());
            docMap.put("fileName", document.getFileName());
            docMap.put("issueDate", document.getIssueDate());
            docMap.put("referenceNumber", document.getReferenceNumber());
            documentDetails.add(docMap);
        }
        response.put("documents", documentDetails);

        return ResponseEntity.status(201).body(response);
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
        response.setStatus(milestone.getStatus().toString());
        response.setCreatedAt(milestone.getCreatedAt());
        response.setUpdatedAt(milestone.getUpdatedAt());
        response.setEnable(milestone.isEnable());
        response.setComplianceId(milestone.getCompliance() != null ? milestone.getCompliance().getId() : null);
        response.setReporterId(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getId() : null);
        response.setAssignedTo(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getId() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssigneeMail(milestone.getAssigneeMail());
        response.setIssuedDate(milestone.getIssuedDate());
        response.setCriticality(milestone.getCriticality());
        response.setRemark(milestone.getRemark());
        response.setBusinessUnitId(milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getId() : null);

        // Step 3: Map Document Details to DocumentDetails DTO
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
                .map(this::mapToMilestoneResponseWithDetails)
                .toList();
    }

    private MilestoneResponse mapToMilestoneResponseWithDetails(MileStone milestone) {
        MilestoneResponse response = new MilestoneResponse();

        // Set basic details
        response.setId(milestone.getId());
        response.setMileStoneName(milestone.getMileStoneName());
        response.setDescription(milestone.getDescription());
        response.setStatus(milestone.getStatus().toString());
        response.setCreatedAt(milestone.getCreatedAt());
        response.setUpdatedAt(milestone.getUpdatedAt());
        response.setEnable(milestone.isEnable());
        response.setComplianceId(milestone.getCompliance() != null ? milestone.getCompliance().getId() : null);
        response.setReporterId(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getId() : null);
        response.setReporterName(milestone.getTaskReporter() != null ? milestone.getTaskReporter().getUserName() : null);
        response.setAssignedTo(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getId() : null);
        response.setAssignedName(milestone.getAssignedTo() != null ? milestone.getAssignedTo().getUserName() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
        response.setAssigneeMail(milestone.getAssigneeMail());
        response.setIssuedDate(milestone.getIssuedDate());
        response.setCriticality(milestone.getCriticality());
        response.setRemark(milestone.getRemark());
        response.setBusinessUnitId(milestone.getBusinessUnit().getId());
        response.setSubscriberId(milestone.getSubscriber().getId());

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
            milestones = milestoneRepository.findBySubscriberAndAssignedToAndStatus(subscriber, user, status);
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

