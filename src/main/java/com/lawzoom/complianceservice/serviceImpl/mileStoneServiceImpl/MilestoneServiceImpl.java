package com.lawzoom.complianceservice.serviceImpl.mileStoneServiceImpl;

import com.lawzoom.complianceservice.dto.commentDto.CommentDetails;
import com.lawzoom.complianceservice.dto.complianceTaskDto.*;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.comments.MileStoneComments;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.RenewalRepository.RenewalRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.service.mileStoneService.MilestoneService;
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
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RenewalRepository renewalRepository;


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

        // Step 8: Validate Milestone Details
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
            milestone.setExpiryDate(milestoneRequest.getExpiryDate());

            // Add Comments (if provided)
            if (milestoneRequest.getComment() != null && !milestoneRequest.getComment().isEmpty()) {
                MileStoneComments comment = new MileStoneComments();
                comment.setCommentText(milestoneRequest.getComment());
                comment.setUser(reporter);
                comment.setMilestone(milestone);
                milestone.getMileStoneComments().add(comment);
            }

            // Add Document (if provided)
            if (milestoneRequest.getFile() != null && !milestoneRequest.getFile().isEmpty()) {
                Document document = new Document();
                document.setDocumentName(milestoneRequest.getDocumentName());
                document.setFile(milestoneRequest.getFile());
                document.setReferenceNumber(milestoneRequest.getReferenceNumber());
                document.setRemarks(milestoneRequest.getRemarks());

                User addedBy = userRepository.findById(milestoneRequest.getManagerId())
                        .orElseThrow(() -> new NotFoundException("Added By user not found with ID: " + milestoneRequest.getManagerId()));
                document.setAddedBy(addedBy);
                document.setMilestone(milestone);
                document.setSubscriber(subscriber);
                document.setCompliance(compliance);

                documentRepository.save(document);
                milestone.getDocuments().add(document);
            }

            // Save Milestone
            milestoneRepository.save(milestone);

            // Save Renewal if applicable
            if (milestoneRequest.getRenewalDate() != null || milestoneRequest.getReminderDurationType() != null) {
                if (milestoneRequest.getRenewalDate() == null || milestoneRequest.getReminderDurationType() == null) {
                    throw new IllegalArgumentException("Both renewal date and reminder duration type must be provided if renewal data is included.");
                }

                Renewal.ReminderDurationType reminderType;
                try {
                    reminderType = Renewal.ReminderDurationType.valueOf(milestoneRequest.getReminderDurationType().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid Reminder Duration Type: " + milestoneRequest.getReminderDurationType());
                }

                Renewal renewal = new Renewal();
                renewal.setMilestone(milestone);
                renewal.setSubscriber(subscriber);
                renewal.setUser(reporter);
                renewal.setIssuedDate(milestoneRequest.getIssuedDate());
                renewal.setExpiryDate(milestoneRequest.getExpiryDate());
                renewal.setRenewalDate(milestoneRequest.getRenewalDate());
                renewal.setReminderDurationType(reminderType);
                renewal.setReminderDurationValue(milestoneRequest.getReminderDurationValue());
                renewal.setRenewalNotes(milestoneRequest.getRenewalNotes());
                renewal.setNotificationsEnabled(milestoneRequest.isNotificationsEnabled());
                renewal.setIssuedDate(milestoneRequest.getIssuedDate());
                renewal.setExpiryDate(milestoneRequest.getExpiryDate());

                renewal.calculateNextReminderDate();

                renewalRepository.save(renewal);
            }

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
            response.put("comment", milestoneRequest.getComment());

            return ResponseEntity.status(201).body(response);
        }

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
        response.setManagerName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
        response.setAssignedId(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
        response.setAssignedName(milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
        response.setIssuedDate(milestone.getIssuedDate());
        response.setStartedDate(milestone.getStartedDate());
        response.setDueDate(milestone.getDueDate());
        response.setCompletedDate(milestone.getCompletedDate());
        response.setCriticality(milestone.getCriticality());
        response.setRemark(milestone.getRemark());
        response.setBusinessUnitId(milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getId() : null);
        response.setSubscriberId(milestone.getSubscriber() != null ? milestone.getSubscriber().getId() : null);
        response.setExpiryDate(milestone.getExpiryDate());

        // Step 3: Map Reminder Details to ReminderDetails DTO
        List<MilestoneResponse.ReminderDetails> reminderDetails = milestone.getReminders().stream().map(reminder -> {
            MilestoneResponse.ReminderDetails remDetails = new MilestoneResponse.ReminderDetails();
            remDetails.setId(reminder.getId());
            remDetails.setReminderDate(reminder.getReminderDate());
            remDetails.setReminderEndDate(reminder.getReminderEndDate());
            remDetails.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
            remDetails.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
            remDetails.setRepeatTimelineType(reminder.getRepeatTimelineType());

            return remDetails;
        }).toList();
        response.setReminders(reminderDetails);

        // Step 4: Map Renewal Details to RenewalDetails DTO
        List<MilestoneResponse.RenewalDetails> renewalDetails = milestone.getRenewals().stream().map(renewal -> {
            MilestoneResponse.RenewalDetails renDetails = new MilestoneResponse.RenewalDetails();
            renDetails.setId(renewal.getId());
            renDetails.setIssuedDate(renewal.getIssuedDate());
            renDetails.setExpiryDate(renewal.getExpiryDate());
            renDetails.setReminderDurationType(renewal.getReminderDurationType().toString()); // Convert enum to String
            renDetails.setReminderDurationValue(renewal.getReminderDurationValue());
            renDetails.setRenewalNotes(renewal.getRenewalNotes());
            renDetails.setNotificationsEnabled(renewal.isNotificationsEnabled());
            renDetails.setReminderFrequency(renewal.getReminderFrequency());
            return renDetails;
        }).toList();
        response.setRenewals(renewalDetails);

        // Step 5: Map Document Details to DocumentDetails DTO
        List<MilestoneResponse.DocumentDetails> documentDetails = milestone.getDocuments().stream().map(document -> {
            MilestoneResponse.DocumentDetails docDetails = new MilestoneResponse.DocumentDetails();
            docDetails.setId(document.getId());
            docDetails.setDocumentName(document.getDocumentName());
            docDetails.setFileName(document.getFile());
            docDetails.setIssueDate(document.getIssueDate());
            docDetails.setReferenceNumber(document.getReferenceNumber());
            docDetails.setRemarks(document.getRemarks());
            docDetails.setUploadDate(document.getUploadDate());
            return docDetails;
        }).toList();
        response.setDocuments(documentDetails);

        // Step 6: Map Comments to CommentDetails DTO
        List<CommentDetails> commentDetails = milestone.getMileStoneComments().stream().map(comment -> {
            CommentDetails commDetails = new CommentDetails();
            commDetails.setId(comment.getId());
            commDetails.setCommentText(comment.getCommentText());
            commDetails.setUserId(comment.getUser() != null ? comment.getUser().getId() : null);
            commDetails.setUserName(comment.getUser() != null ? comment.getUser().getUserName() : null);
            commDetails.setCreatedAt(comment.getCreatedAt());
            return commDetails;
        }).toList();
        response.setComments(commentDetails);

        return response;
    }



    @Override
    public List<MilestoneListResponse> fetchAllMilestones(MilestoneRequestForFetch request) {
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

        // Step 3: Map milestones to response DTOs manually
        List<MilestoneListResponse> milestoneResponses = new ArrayList<>();

        for (MileStone milestone : milestones) {
            MilestoneListResponse response = new MilestoneListResponse();

            response.setId(milestone.getId());
            response.setMileStoneName(milestone.getMileStoneName());
            response.setDescription(milestone.getDescription());
            response.setStatusId(milestone.getStatus().getId());
            response.setStatus(milestone.getStatus().getName());
            response.setCreatedAt(milestone.getCreatedAt());
            response.setUpdatedAt(milestone.getUpdatedAt());
            response.setEnable(milestone.isEnable());
            response.setComplianceId(milestone.getCompliance().getId());
            response.setBusinessUnitId(milestone.getBusinessUnit().getId());
            response.setSubscriberId(subscriber.getId());

            // Manually set manager details
            if (milestone.getManager() != null) {
                response.setManagerId(milestone.getManager().getId());
                response.setManagerName(milestone.getManager().getUserName());
            }

            // Manually set assigned user details
            if (milestone.getAssigned() != null) {
                response.setAssignedId(milestone.getAssigned().getId());
                response.setAssignedName(milestone.getAssigned().getUserName());
            }

            // Manually set the user who assigned the task
            if (milestone.getAssignedBy() != null) {
                response.setAssignedBy(milestone.getAssignedBy().getId());
                response.setAssignedByName(milestone.getAssignedBy().getUserName());
            }

            response.setIssuedDate(milestone.getIssuedDate());
            response.setStartedDate(milestone.getStartedDate());
            response.setDueDate(milestone.getDueDate());
            response.setCompletedDate(milestone.getCompletedDate());
            response.setCriticality(milestone.getCriticality());
            response.setRemark(milestone.getRemark());

            // Add to the list
            milestoneResponses.add(response);
        }

        return milestoneResponses;
    }


    private MilestoneResponse mapToMilestoneResponseWithDetails(MileStone milestone) {
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
        response.setManagerName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
        response.setAssignedId(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
        response.setAssignedName(milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
        response.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
        response.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
        response.setIssuedDate(milestone.getIssuedDate());
        response.setStartedDate(milestone.getStartedDate());
        response.setDueDate(milestone.getDueDate());
        response.setCompletedDate(milestone.getCompletedDate());
        response.setCriticality(milestone.getCriticality());
        response.setRemark(milestone.getRemark());
        response.setBusinessUnitId(milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getId() : null);
        response.setSubscriberId(milestone.getSubscriber() != null ? milestone.getSubscriber().getId() : null);

        List<MilestoneResponse.ReminderDetails> reminderDetails = milestone.getReminders().stream()
                .map(reminder -> {
                    MilestoneResponse.ReminderDetails rd = new MilestoneResponse.ReminderDetails();
                    rd.setId(reminder.getId());
                    rd.setReminderDate(reminder.getReminderDate());
                    rd.setReminderEndDate(reminder.getReminderEndDate());
                    rd.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
                    rd.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
                    rd.setRepeatTimelineType(reminder.getRepeatTimelineType());
                    return rd;
                })
                .toList();
        response.setReminders(reminderDetails);

        // Map renewals
        List<MilestoneResponse.RenewalDetails> renewalDetails = milestone.getRenewals().stream()
                .map(renewal -> {
                    MilestoneResponse.RenewalDetails rd = new MilestoneResponse.RenewalDetails();
                    rd.setId(renewal.getId());
                    rd.setIssuedDate(renewal.getIssuedDate());
                    rd.setExpiryDate(renewal.getExpiryDate());
                    rd.setReminderDurationType(renewal.getReminderDurationType().toString()); // Convert enum to String
                    rd.setReminderDurationValue(renewal.getReminderDurationValue());
                    rd.setRenewalNotes(renewal.getRenewalNotes());
                    rd.setNotificationsEnabled(renewal.isNotificationsEnabled());
                    rd.setReminderFrequency(renewal.getReminderFrequency());
                    return rd;
                })
                .toList();
        response.setRenewals(renewalDetails);

        // Map documents
        List<MilestoneResponse.DocumentDetails> documentDetails = milestone.getDocuments().stream()
                .map(document -> {
                    MilestoneResponse.DocumentDetails dd = new MilestoneResponse.DocumentDetails();
                    dd.setId(document.getId());
                    dd.setDocumentName(document.getDocumentName());
                    dd.setFileName(document.getFile());
                    dd.setIssueDate(document.getIssueDate());
                    dd.setReferenceNumber(document.getReferenceNumber());
                    dd.setRemarks(document.getRemarks());
                    dd.setUploadDate(document.getUploadDate());
                    return dd;
                })
                .toList();
        response.setDocuments(documentDetails);

        // Map tasks
        List<TaskMileStoneResponse> taskResponses = milestone.getTasks().stream()
                .map(task -> {
                    TaskMileStoneResponse taskResponse = new TaskMileStoneResponse();
                    taskResponse.setId(task.getId());
                    taskResponse.setName(task.getName());
                    taskResponse.setDescription(task.getDescription());
                    taskResponse.setDate(task.getDate());
                    taskResponse.setStatusId(task.getStatus() != null ? task.getStatus().getId() : null);
                    taskResponse.setStatusName(task.getStatus() != null ? task.getStatus().getName() : null);
                    taskResponse.setCreatedAt(task.getCreatedAt());
                    taskResponse.setUpdatedAt(task.getUpdatedAt());
                    taskResponse.setEnable(task.isEnable());
                    taskResponse.setStartDate(task.getStartDate());
                    taskResponse.setDueDate(task.getDueDate());
                    taskResponse.setCompletedDate(task.getCompletedDate());
                    taskResponse.setManagerId(task.getManager() != null ? task.getManager().getId() : null);
                    taskResponse.setManagerName(task.getManager() != null ? task.getManager().getUserName() : null);
                    taskResponse.setAssigneeId(task.getAssignee() != null ? task.getAssignee().getId() : null);
                    taskResponse.setAssigneeName(task.getAssignee() != null ? task.getAssignee().getUserName() : null);
                    taskResponse.setCriticality(task.getCriticality());

                    taskResponse.setCriticality(task.getCriticality());
                    return taskResponse;
                })
                .toList();
        response.setTasks(taskResponses);

        return response;
    }


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
            response.setManagerName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
            response.setAssignedId(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
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



    @Override
    public List<MilestoneDetailsResponse> allMileStones(Long userId, Long subscriberId) {
        // Step 1: Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Error: User not found!"));

        // Step 2: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Error: Subscriber not found!"));

        // Step 3: Fetch milestones for the subscriber
        List<MileStone> milestones = milestoneRepository.findBySubscriber(subscriber);

        // Step 4: Map milestones to MilestoneDetailsResponse
        List<MilestoneDetailsResponse> milestoneDetailsResponses = new ArrayList<>();

        for (MileStone milestone : milestones) {
            MilestoneDetailsResponse response = new MilestoneDetailsResponse();
            response.setCompanyId(milestone.getBusinessUnit().getGstDetails().getCompany().getId());
            response.setCompanyName(milestone.getBusinessUnit().getGstDetails().getCompany().getCompanyName());
            response.setBusinessUnit(milestone.getBusinessUnit().getId());
            response.setBusinessName(milestone.getBusinessUnit().getAddress());
            response.setBusinessActivityId(milestone.getBusinessUnit().getBusinessActivity() != null
                    ? milestone.getBusinessUnit().getBusinessActivity().getId()
                    : null);
            response.setBusinessActivityName(milestone.getBusinessUnit().getBusinessActivity() != null
                    ? milestone.getBusinessUnit().getBusinessActivity().getBusinessActivityName()
                    : null);
            response.setComplianceId(milestone.getCompliance().getId());
            response.setComplianceName(milestone.getCompliance().getComplianceName());
            response.setMileStoneId(milestone.getId());
            response.setMileStoneName(milestone.getMileStoneName());
            response.setCriticality(milestone.getCriticality());
            response.setStartedDate(milestone.getStartedDate());
            response.setDueDate(milestone.getDueDate());
            response.setStatusName(milestone.getStatus() != null ? milestone.getStatus().getName() : "Not Started");
            response.setManagerId(milestone.getManager() != null ? milestone.getManager().getId() : null);
            response.setManagerName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);

            milestoneDetailsResponses.add(response);
        }

        return milestoneDetailsResponses;
    }



}

