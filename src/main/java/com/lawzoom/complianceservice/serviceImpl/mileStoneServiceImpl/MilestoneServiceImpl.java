package com.lawzoom.complianceservice.serviceImpl.mileStoneServiceImpl;

import com.lawzoom.complianceservice.dto.commentDto.CommentDetails;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.*;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.comments.MileStoneComments;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.ReminderRepositroy.ReminderRepository;
import com.lawzoom.complianceservice.repository.RenewalRepository.RenewalRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.service.mileStoneService.MilestoneService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TaskRepository taskRepository;

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

    @Autowired
    private ReminderRepository reminderRepository;


    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> createMilestone(MilestoneRequest milestoneRequest) {
        // Step 1: Validate and Fetch Required Entities
        Compliance compliance = fetchCompliance(milestoneRequest.getComplianceId());
        BusinessUnit businessUnit = fetchBusinessUnit(milestoneRequest.getBusinessUnitId());
        User reporter = fetchUser(milestoneRequest.getManagerId(), "Reporter");
        User assignedToUser = fetchOptionalUser(milestoneRequest.getAssigneeId(), "Assigned To");
        User assignedByUser = fetchOptionalUser(milestoneRequest.getAssignedBy(), "Assigned By");
        Subscriber subscriber = fetchSubscriber(milestoneRequest.getSubscriberId());
        Status status = fetchStatus(milestoneRequest.getStatusId());

        // Step 2: Create and Populate Milestone
        MileStone milestone = new MileStone();
        populateMilestone(milestone, milestoneRequest, compliance, businessUnit, reporter, assignedToUser, assignedByUser, subscriber, status);

        // Step 3: Save Milestone (Save first to avoid transient property issues)
        milestone = milestoneRepository.save(milestone);

        // Step 4: Handle Comments
        if (milestoneRequest.getComment() != null && !milestoneRequest.getComment().isEmpty()) {
            MileStoneComments comment = createComment(milestoneRequest.getComment(), reporter, milestone);
            milestone.getMileStoneComments().add(comment);
        }

        // Step 5: Handle Document
        if (milestoneRequest.getFile() != null && !milestoneRequest.getFile().isEmpty()) {
            Document document = createDocument(milestoneRequest, reporter, milestone, subscriber, compliance);
            milestone.getDocuments().add(document);
        }

        // Step 6: Handle Renewal and Reminder (if applicable)
        if (status.getId() != 1) {
            handleRenewal(milestoneRequest, milestone, subscriber, reporter);
            handleReminder(milestoneRequest, milestone, subscriber, reporter);
        }

        // Step 7: Prepare Response
        Map<String, Object> response = prepareResponse(milestone);
        return ResponseEntity.status(201).body(response);
    }

    private Compliance fetchCompliance(Long complianceId) {
        return complianceRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));
    }

    private BusinessUnit fetchBusinessUnit(Long businessUnitId) {
        return businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new NotFoundException("Business Unit not found with ID: " + businessUnitId));
    }

    private User fetchUser(Long userId, String roleDescription) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(roleDescription + " not found with ID: " + userId));
    }

    private User fetchOptionalUser(Long userId, String roleDescription) {
        if (userId == null) return null;
        return fetchUser(userId, roleDescription);
    }

    private Subscriber fetchSubscriber(Long subscriberId) {
        return subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscriberId: " + subscriberId));
    }

    private Status fetchStatus(Long statusId) {
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + statusId));
    }


    private void populateMilestone(MileStone milestone, MilestoneRequest milestoneRequest, Compliance compliance,
                                   BusinessUnit businessUnit, User reporter, User assignedToUser, User assignedByUser,
                                   Subscriber subscriber, Status status) {
        milestone.setMileStoneName(milestoneRequest.getMileStoneName());
        milestone.setDescription(milestoneRequest.getDescription());
        milestone.setStartedDate(milestoneRequest.getStartedDate());
        milestone.setDueDate(milestoneRequest.getDueDate());
        milestone.setIssuedDate(milestoneRequest.getIssuedDate());
        milestone.setExpiryDate(milestoneRequest.getExpiryDate());
        milestone.setCompliance(compliance);
        milestone.setBusinessUnit(businessUnit);
        milestone.setManager(reporter);
        milestone.setAssigned(assignedToUser);
        milestone.setAssignedBy(assignedByUser);
        milestone.setCreatedAt(new Date());
        milestone.setUpdatedAt(new Date());
        milestone.setCriticality(milestoneRequest.getCriticality());
        milestone.setStatus(status);
        milestone.setSubscriber(subscriber);
        milestone.setRemark(milestoneRequest.getRemark());
        milestone.setCompletedDate(milestoneRequest.getCompletedDate());
    }


    private MileStoneComments createComment(String commentText, User reporter, MileStone milestone) {
        MileStoneComments comment = new MileStoneComments();
        comment.setCommentText(commentText);
        comment.setUser(reporter);
        comment.setMilestone(milestone);
        return comment;
    }


    private Document createDocument(MilestoneRequest milestoneRequest, User reporter, MileStone milestone,
                                    Subscriber subscriber, Compliance compliance) {
        Document document = new Document();
        document.setDocumentName(milestoneRequest.getDocumentName());
        document.setFile(milestoneRequest.getFile());
        document.setReferenceNumber(milestoneRequest.getReferenceNumber());
        document.setRemarks(milestoneRequest.getRemark());
        document.setAddedBy(reporter);
        document.setMilestone(milestone);
        document.setSubscriber(subscriber);
        document.setCompliance(compliance);
        return documentRepository.save(document);
    }

    private void handleRenewal(MilestoneRequest milestoneRequest, MileStone milestone, Subscriber subscriber, User reporter) {
        if (milestoneRequest.getRenewalRequest() == null) return;

        RenewalRequest renewalRequest = milestoneRequest.getRenewalRequest();
        if (renewalRequest.getRenewalDate() == null || renewalRequest.getReminderDurationType() == null) {
            throw new IllegalArgumentException("Both renewal date and reminder duration type must be provided.");
        }

        Renewal renewal = new Renewal();
        renewal.setMilestone(milestone);
        renewal.setSubscriber(subscriber);
        renewal.setUser(reporter);
        renewal.setIssuedDate(renewalRequest.getIssuedDate());
        renewal.setExpiryDate(renewalRequest.getExpiryDate());
        renewal.setRenewalDate(renewalRequest.getRenewalDate());
        renewal.setReminderDurationType(
                Renewal.ReminderDurationType.valueOf(renewalRequest.getReminderDurationType().toUpperCase())
        );
        renewal.setReminderDurationValue(renewalRequest.getReminderDurationValue());
        renewal.setRenewalNotes(renewalRequest.getRenewalNotes());
        renewal.setNotificationsEnabled(renewalRequest.isNotificationsEnabled());
        renewal.setCertificateTypeDuration(
                Renewal.ReminderDurationType.valueOf(renewalRequest.getCertificateTypeDuration().toUpperCase())
        );
        renewal.setCertificateDurationValue(renewalRequest.getCertificateDurationValue());
        renewal.calculateNextReminderDate();

        renewalRepository.save(renewal);
    }

    private void handleReminder(MilestoneRequest milestoneRequest, MileStone milestone, Subscriber subscriber, User reporter) {
        if (milestoneRequest.getReminderRequest() == null) return;

        ReminderRequest reminderRequest = milestoneRequest.getReminderRequest();
        Reminder reminder = new Reminder();
        reminder.setMilestone(milestone);
        reminder.setSubscriber(subscriber);
        reminder.setCreatedBy(reporter);
        reminder.setReminderDate(reminderRequest.getReminderDate());
        reminder.setReminderEndDate(reminderRequest.getReminderEndDate());
        reminder.setNotificationTimelineValue(reminderRequest.getNotificationTimelineValue());
        reminder.setRepeatTimelineValue(reminderRequest.getRepeatTimelineValue());
        reminder.setRepeatTimelineType(reminderRequest.getRepeatTimelineType());
        reminder.setStopFlag(reminderRequest.getStopFlag());

        reminderRepository.save(reminder);
    }


    private Map<String, Object> prepareResponse(MileStone milestone) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", milestone.getId());
        response.put("mileStoneName", milestone.getMileStoneName());
        response.put("description", milestone.getDescription());
        response.put("status", milestone.getStatus().getName());
        response.put("createdAt", milestone.getCreatedAt());
        response.put("updatedAt", milestone.getUpdatedAt());
        response.put("isEnable", milestone.isEnable());
        response.put("complianceId", milestone.getCompliance().getId());
        response.put("reporterId", milestone.getManager().getId());
        response.put("remark", milestone.getRemark());
        return response;
    }



//    @Override
//    public List<MilestoneListResponse> fetchAllMilestones(MilestoneRequestForFetch request) {
//        // Step 1: Validate Subscriber
//        Subscriber subscriber = subscriberRepository.findById(request.getSubscriberId())
//                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + request.getSubscriberId()));
//
//        // Step 2: Fetch Milestones
//        List<MileStone> milestones;
//        if (isSuperAdmin(request.getUserId())) {
//            // Case 1: User is SUPER_ADMIN
//            milestones = milestoneRepository.findMilestonesBySubscriber(subscriber.getId());
//        } else {
//            // Case 2: User is USER
//            User user = userRepository.findById(request.getUserId())
//                    .orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId()));
//
//            milestones = milestoneRepository.findMilestonesBySubscriberAndAssignedTo(subscriber.getId(), user.getId());
//        }
//
//        // Step 3: Map milestones to response DTOs manually
//        List<MilestoneListResponse> milestoneResponses = new ArrayList<>();
//
//        for (MileStone milestone : milestones) {
//            MilestoneListResponse response = new MilestoneListResponse();
//
//            response.setId(milestone.getId());
//            response.setMileStoneName(milestone.getMileStoneName());
//            response.setDescription(milestone.getDescription());
//            response.setStatusId(milestone.getStatus().getId());
//            response.setStatus(milestone.getStatus().getName());
//            response.setCreatedAt(milestone.getCreatedAt());
//            response.setUpdatedAt(milestone.getUpdatedAt());
//            response.setEnable(milestone.isEnable());
//            response.setComplianceId(milestone.getCompliance().getId());
//            response.setBusinessUnitId(milestone.getBusinessUnit().getId());
//            response.setSubscriberId(subscriber.getId());
//
//            // Manually set manager details
//            if (milestone.getManager() != null) {
//                response.setManagerId(milestone.getManager().getId());
//                response.setManagerName(milestone.getManager().getUserName());
//            }
//
//            // Manually set assigned user details
//            if (milestone.getAssigned() != null) {
//                response.setAssignedId(milestone.getAssigned().getId());
//                response.setAssignedName(milestone.getAssigned().getUserName());
//            }
//
//            // Manually set the user who assigned the task
//            if (milestone.getAssignedBy() != null) {
//                response.setAssignedBy(milestone.getAssignedBy().getId());
//                response.setAssignedByName(milestone.getAssignedBy().getUserName());
//            }
//
//            response.setIssuedDate(milestone.getIssuedDate());
//            response.setStartedDate(milestone.getStartedDate());
//            response.setDueDate(milestone.getDueDate());
//            response.setCompletedDate(milestone.getCompletedDate());
//            response.setCriticality(milestone.getCriticality());
//            response.setRemark(milestone.getRemark());
//
//            // Add to the list
//            milestoneResponses.add(response);
//        }
//
//        return milestoneResponses;
//    }


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
    @Transactional
    public Map<String, Object> updateMilestone(MilestoneUpdateRequest updateRequest) {
        // Step 1: Fetch the existing milestone
        MileStone milestone = milestoneRepository.findById(updateRequest.getMilestoneId())
                .orElseThrow(() -> new EntityNotFoundException("Milestone not found with ID: " + updateRequest.getMilestoneId()));

        // Step 2: Update required fields if provided
        if (updateRequest.getMileStoneName() != null) {
            milestone.setMileStoneName(updateRequest.getMileStoneName());
        }
        if (updateRequest.getDescription() != null) {
            milestone.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getDueDate() != null) {
            milestone.setDueDate(updateRequest.getDueDate());
        }
        if (updateRequest.getCompletedDate() != null) {
            milestone.setCompletedDate(updateRequest.getCompletedDate());
        }
        if (updateRequest.getStatusId() != null) {
            Status status = statusRepository.findById(updateRequest.getStatusId())
                    .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: " + updateRequest.getStatusId()));
            milestone.setStatus(status);
        }
        if (updateRequest.getManagerId() != null) {
            User manager = userRepository.findById(updateRequest.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with ID: " + updateRequest.getManagerId()));
            milestone.setManager(manager);
        }
        if (updateRequest.getAssigneeId() != null) {
            User assignedUser = userRepository.findById(updateRequest.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignee not found with ID: " + updateRequest.getAssigneeId()));
            milestone.setAssigned(assignedUser);
        }
        if (updateRequest.getRemark() != null) {
            milestone.setRemark(updateRequest.getRemark());
        }
        if (updateRequest.getCriticality() != null) {
            milestone.setCriticality(updateRequest.getCriticality());
        }

        // Step 3: Save the updated milestone
        milestone = milestoneRepository.save(milestone);

        // Step 4: Manually construct response map
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Milestone updated successfully");
        response.put("milestoneId", milestone.getId());
        response.put("mileStoneName", milestone.getMileStoneName());
        response.put("description", milestone.getDescription());
        response.put("dueDate", milestone.getDueDate());
        response.put("completedDate", milestone.getCompletedDate());
        response.put("status", milestone.getStatus() != null ? milestone.getStatus().getName() : null);
        response.put("manager", milestone.getManager() != null ? milestone.getManager().getUserName() : null);
        response.put("assignedTo", milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
        response.put("criticality", milestone.getCriticality());
        response.put("remark", milestone.getRemark());

        return response;
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
//


    @Override
    public Map<String, Object> fetchUserAllMilestones(Long userId, Long subscriberId, Pageable pageable) {
        // Step 1: Validate User and Subscriber
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Error: User not found!"));

        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Error: Subscriber not found!"));

        // Step 2: Determine User Role
        boolean isSuperAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        Page<MileStone> milestonePage;

        // Step 3: Fetch Milestones Based on Role
        if (isSuperAdmin || isAdmin) {
            // Fetch all milestones for the given subscriber
            milestonePage = milestoneRepository.findBySubscriber(subscriber, pageable);
        } else {
            // Fetch milestones where the user is a manager or assigned user
            List<MileStone> managerMilestones = milestoneRepository.findByManager(user);
            List<MileStone> assignedMilestones = milestoneRepository.findByAssigned(user);

            // Combine both lists and remove duplicates
            Set<MileStone> combinedMilestones = new HashSet<>(managerMilestones);
            combinedMilestones.addAll(assignedMilestones);

            // Convert to pageable response
            List<MileStone> milestoneList = new ArrayList<>(combinedMilestones);
            milestonePage = new PageImpl<>(milestoneList, pageable, milestoneList.size());
        }

        // Step 4: Prepare the response Map
        Map<String, Object> response = new HashMap<>();
        response.put("milestones", milestonePage.stream().map(this::mapToMilestoneMap).toList());

        // Pagination details
        response.put("milestonePageNumber", milestonePage.getNumber());
        response.put("milestonePageSize", milestonePage.getSize());
        response.put("totalMilestoneElements", milestonePage.getTotalElements());
        response.put("totalMilestonePages", milestonePage.getTotalPages());
        response.put("lastMilestonePage", milestonePage.isLast());

        return response;
    }


    private Map<String, Object> mapToMilestoneMap(MileStone milestone) {
        Map<String, Object> milestoneMap = new HashMap<>();
        milestoneMap.put("companyId", milestone.getBusinessUnit().getGstDetails().getCompany().getId());
        milestoneMap.put("companyName", milestone.getBusinessUnit().getGstDetails().getCompany().getCompanyName());
        milestoneMap.put("businessUnit", milestone.getBusinessUnit().getId());
        milestoneMap.put("businessName", milestone.getBusinessUnit().getAddress());
        milestoneMap.put("businessActivityId", milestone.getBusinessUnit().getBusinessActivity() != null
                ? milestone.getBusinessUnit().getBusinessActivity().getId()
                : null);
        milestoneMap.put("businessActivityName", milestone.getBusinessUnit().getBusinessActivity() != null
                ? milestone.getBusinessUnit().getBusinessActivity().getBusinessActivityName()
                : null);
        milestoneMap.put("complianceId", milestone.getCompliance().getId());
        milestoneMap.put("complianceName", milestone.getCompliance().getComplianceName());
        milestoneMap.put("mileStoneId", milestone.getId());
        milestoneMap.put("mileStoneName", milestone.getMileStoneName());
        milestoneMap.put("criticality", milestone.getCriticality());
        milestoneMap.put("startedDate", milestone.getStartedDate());
        milestoneMap.put("dueDate", milestone.getDueDate());
        milestoneMap.put("statusName", milestone.getStatus() != null ? milestone.getStatus().getName() : "Not Started");
        milestoneMap.put("managerId", milestone.getManager() != null ? milestone.getManager().getId() : null);
        milestoneMap.put("managerName", milestone.getManager() != null ? milestone.getManager().getUserName() : null);

        return milestoneMap;
    }


    @Override
    public Map<String, Object> fetchMilestoneById(Long milestoneId) {
        // Step 1: Fetch milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new EntityNotFoundException("Milestone not found"));

        // Step 2: Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("milestoneId", milestone.getId());
        response.put("mileStoneName", milestone.getMileStoneName());
        response.put("description", milestone.getDescription());
        response.put("startedDate", milestone.getStartedDate());
        response.put("dueDate", milestone.getDueDate());
        response.put("completedDate", milestone.getCompletedDate());
        response.put("issuedDate", milestone.getIssuedDate());
        response.put("expiryDate", milestone.getExpiryDate());
        response.put("criticality", milestone.getCriticality());
        response.put("remark", milestone.getRemark());
        response.put("status", milestone.getStatus() != null ? milestone.getStatus().getName() : null);
        response.put("businessUnit", milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getBusinessActivity().getBusinessActivityName() : null);
        response.put("businessActivity", milestone.getBusinessActivity() != null ? milestone.getBusinessActivity().getBusinessActivityName() : null);
        response.put("compliance", milestone.getCompliance() != null ? milestone.getCompliance().getComplianceName() : null);
        response.put("subscriber", milestone.getSubscriber() != null ? milestone.getSubscriber() : null);
        response.put("createdAt", milestone.getCreatedAt());
        response.put("updatedAt", milestone.getUpdatedAt());

        // Step 3: Fetch related entities
        response.put("manager", milestone.getManager() != null ? milestone.getManager().getUserName() : null);
        response.put("assignedTo", milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
        response.put("assignedBy", milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);

        // Step 4: Fetch Comments
        response.put("comments", milestone.getMileStoneComments().stream()
                .map(comment -> Map.of(
                        "commentId", comment.getId(),
                        "commentText", comment.getCommentText(),
                        "commentedBy", comment.getUser().getUserName(),
                        "createdAt", comment.getCreatedAt()))
                .toList());

        // Step 5: Fetch Tasks
        response.put("tasks", milestone.getTasks().stream()
                .map(task -> Map.of(
                        "taskId", task.getId(),
                        "taskName", task.getName(),
                        "status", task.getStatus().getName(),
                        "assignee", task.getAssignee() != null ? task.getAssignee().getUserName() : null))
                .toList());

        // Step 6: Fetch Documents
        response.put("documents", milestone.getDocuments().stream()
                .map(document -> Map.of(
                        "documentId", document.getId(),
                        "documentName", document.getDocumentName(),
                        "fileURL", document.getFileURL(),
                        "uploadedBy", document.getAddedBy() != null ? document.getAddedBy().getUserName() : null,
                        "uploadDate", document.getUploadDate()))
                .toList());

        // Step 7: Fetch Reminders
        response.put("reminders", milestone.getReminders().stream()
                .map(reminder -> Map.of(
                        "reminderId", reminder.getId(),
                        "reminderDate", reminder.getReminderDate(),
                        "reminderEndDate", reminder.getReminderEndDate(),
                        "createdBy", reminder.getCreatedBy().getUserName(),
                        "repeatTimelineType", reminder.getRepeatTimelineType(),
                        "repeatTimelineValue", reminder.getRepeatTimelineValue()))
                .toList());

        // Step 8: Fetch Renewals
        response.put("renewals", milestone.getRenewals().stream()
                .map(renewal -> Map.of(
                        "renewalId", renewal.getId(),
                        "issuedDate", renewal.getIssuedDate(),
                        "expiryDate", renewal.getExpiryDate(),
                        "renewalDate", renewal.getRenewalDate(),
                        "reminderDurationValue", renewal.getReminderDurationValue(),
                        "reminderStartDate", renewal.getReminderStartDate(),
                        "subscriber", renewal.getSubscriber()))
                .toList());

        return response;
    }


}

