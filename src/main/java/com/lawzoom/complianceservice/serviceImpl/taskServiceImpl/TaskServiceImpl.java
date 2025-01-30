package com.lawzoom.complianceservice.serviceImpl.taskServiceImpl;


import com.lawzoom.complianceservice.dto.DocumentRequest;
import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskReminderRequest;
import com.lawzoom.complianceservice.dto.taskDto.TaskRenewalRequest;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.renewalModel.TaskRenewal;
import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.service.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TaskReminderRepository taskReminderRepository;

    @Autowired

    private SubscriberRepository subscriberRepository;

    @Autowired
    private TaskRenewalRepository taskRenewalRepository;

    @Autowired
    private DocumentRepository documentRepository;



    @Override
    public TaskListResponse createTask(TaskRequest taskRequest, Long userId) {

        // Validate and fetch necessary entities
        MileStone milestone = milestoneRepository.findById(taskRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + taskRequest.getMilestoneId()));

        User manager = userRepository.findActiveUserById(taskRequest.getManagerId());
        if (manager == null) {
            throw new NotFoundException("Active manager not found with ID: " + taskRequest.getManagerId());
        }

        User assignee = userRepository.findActiveUserById(taskRequest.getAssigneeId());
        if (assignee == null) {
            throw new NotFoundException("Active assignee not found with ID: " + taskRequest.getAssigneeId());
        }

        User createdBy = userRepository.findActiveUserById(userId);
        if (createdBy == null) {
            throw new NotFoundException("Active user not found with ID: " + userId);
        }

        Status status = statusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + taskRequest.getStatusId()));

       Subscriber subscriber= subscriberRepository.findById(taskRequest.getSubscriberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscriberId: " + taskRequest.getSubscriberId()));

        // Create and save the task
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(status);
        task.setStartDate(taskRequest.getStartDate());
        task.setDueDate(taskRequest.getDueDate());
        task.setCriticality(taskRequest.getCriticality());
        task.setMilestone(milestone);
        task.setManager(manager);
        task.setAssignee(assignee);
        task.setCreatedByUser(createdBy);
        task.setRemark(taskRequest.getRemark());
        task.setEnable(true); // New field
        task.setSubscriber(subscriber);

        Task savedTask = taskRepository.save(task);

        // Save Documents
        if (taskRequest.getDocuments() != null && !taskRequest.getDocuments().isEmpty()) {
            for (DocumentRequest documentRequest : taskRequest.getDocuments()) {
                Document document = new Document();
                document.setTask(savedTask);
                document.setFile(documentRequest.getFile());
                documentRepository.save(document);
            }
        }



        // Save TaskReminders
        if (taskRequest.getReminders() != null && !taskRequest.getReminders().isEmpty()) {
            for (TaskReminderRequest reminderRequest : taskRequest.getReminders()) {
                TaskReminder taskReminder = new TaskReminder();
                taskReminder.setTask(savedTask);
                taskReminder.setCreatedBy(createdBy);
                taskReminder.setReminderDate(reminderRequest.getReminderDate());
                taskReminder.setReminderEndDate(reminderRequest.getReminderEndDate());
                taskReminder.setNotificationTimelineValue(reminderRequest.getNotificationTimelineValue());
                taskReminder.setRepeatTimelineValue(reminderRequest.getRepeatTimelineValue());
                taskReminder.setRepeatTimelineType(reminderRequest.getRepeatTimelineType());
                taskReminder.setStopFlag(reminderRequest.getStopFlag());
                taskReminderRepository.save(taskReminder);
            }
        }

        // Save TaskRenewals
        if (taskRequest.getRenewals() != null && !taskRequest.getRenewals().isEmpty()) {
            for (TaskRenewalRequest renewalRequest : taskRequest.getRenewals()) {
                TaskRenewal taskRenewal = new TaskRenewal();
                taskRenewal.setTask(savedTask);
                taskRenewal.setUser(createdBy);
                taskRenewal.setIssuedDate(renewalRequest.getIssuedDate());
                taskRenewal.setExpiryDate(renewalRequest.getExpiryDate());
                taskRenewal.setRenewalDate(renewalRequest.getRenewalDate());
                taskRenewal.setReminderDurationType(Renewal.ReminderDurationType.valueOf(renewalRequest.getReminderDurationType().toUpperCase()));
                taskRenewal.setReminderDurationValue(renewalRequest.getReminderDurationValue());
                taskRenewal.setRenewalNotes(renewalRequest.getRenewalNotes());
                taskRenewal.setNotificationsEnabled(renewalRequest.isNotificationsEnabled());
                taskRenewal.setCertificateTypeDuration(Renewal.ReminderDurationType.valueOf(renewalRequest.getCertificateTypeDuration().toUpperCase()));
                taskRenewal.setCertificateDurationValue(renewalRequest.getCertificateDurationValue());
                taskRenewalRepository.save(taskRenewal);
            }
        }

        // Map and return the response
        return mapTaskToResponse(savedTask);
    }


    // Helper Method
    private TaskListResponse mapTaskToResponse(Task task) {
        TaskListResponse response = new TaskListResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus().getName());
        response.setStartDate(task.getStartDate());
        response.setDueDate(task.getDueDate());
        response.setCriticality(task.getCriticality());
        response.setManagerId(task.getManager().getId());
        response.setManagerName(task.getManager().getUserName());
        response.setAssigneeUserId(task.getAssignee().getId());
        response.setAssigneeUserName(task.getAssignee().getUserName());
        response.setMilestoneId(task.getMilestone().getId());
        response.setMilestoneName(task.getMilestone().getMileStoneName());
        response.setRemark(task.getRemark());
        return response;
    }



    @Override
    public List<TaskListResponse> fetchTasks(Long milestoneId) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Fetch Tasks
        List<Task> tasks = taskRepository.findByMilestone(milestone.getId());

        // Map Tasks to Response DTOs manually
        List<TaskListResponse> responses = new ArrayList<>();
        for (Task task : tasks) {
            TaskListResponse response = new TaskListResponse();
            response.setId(task.getId());
            response.setName(task.getName());
            response.setDescription(task.getDescription());
            response.setStatus(task.getStatus().getName());
            response.setStartDate(task.getStartDate());
            response.setDueDate(task.getDueDate());
            response.setCompletedDate(task.getCompletedDate());
            response.setCriticality(task.getCriticality());
            response.setManagerId(task.getManager().getId());
            response.setManagerName(task.getManager().getUserName());
            response.setAssigneeUserId(task.getAssignee().getId());
            response.setAssigneeUserName(task.getAssignee().getUserName());
            response.setMilestoneId(task.getMilestone().getId());
            response.setMilestoneName(task.getMilestone().getMileStoneName());
            response.setRemark(task.getRemark());
            responses.add(response);

        }

        return responses;
    }




    @Override
    public TaskListResponse updateTaskAssignment(Long taskId, Long assigneeUserId, Long reporterUserId) {
        // Validate Task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));

        // Validate Assignee User
        User assignee = userRepository.findById(assigneeUserId)
                .orElseThrow(() -> new NotFoundException("Assignee not found with ID: " + assigneeUserId));

        // Validate Reporter User
        User reporter = userRepository.findById(reporterUserId)
                .orElseThrow(() -> new NotFoundException("Reporter not found with ID: " + reporterUserId));

        // Update Task with new Assignee and Reporter
        task.setAssignee(assignee);
        task.setManager(reporter);
        task.setUpdatedAt(new Date());

        // Save Updated Task
        Task updatedTask = taskRepository.save(task);

        // Create and populate TaskResponse manually
        TaskListResponse response = new TaskListResponse();
        response.setId(updatedTask.getId());
        response.setName(updatedTask.getName());
        response.setDescription(updatedTask.getDescription());
        response.setStatus(updatedTask.getStatus().getName());
        response.setStartDate(updatedTask.getStartDate());
        response.setDueDate(updatedTask.getDueDate());
        response.setCompletedDate(updatedTask.getCompletedDate());
        response.setCriticality(updatedTask.getCriticality());
        response.setManagerId(task.getManager().getId());
        response.setManagerName(task.getManager().getUserName());
        response.setAssigneeUserId(task.getAssignee().getId());
        response.setAssigneeUserName(task.getAssignee().getUserName());
        response.setMilestoneId(updatedTask.getMilestone().getId());
        response.setMilestoneName(updatedTask.getMilestone().getMileStoneName());

        return response;
    }

    @Override
    public Map<String, Object> fetchAllTask(Long userId, Long subscriberId, Pageable pageable) {
        // Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found with ID: " + subscriberId));

        // Determine User Role
        boolean isSuperAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        Page<Task> taskPage;

        if (isSuperAdmin || isAdmin) {
            // Fetch all tasks for SUPER_ADMIN or ADMIN
            taskPage = taskRepository.findBySubscriber(subscriber, pageable);
        } else {
            // Fetch tasks where the user is a Manager
            List<Task> managerTasks = taskRepository.findByManager(user);

            // Fetch tasks where the user is an Assignee
            List<Task> assigneeTasks = taskRepository.findByAssignee(user);

            // Combine both lists and create a Page object manually
            List<Task> combinedTasks = new ArrayList<>();
            combinedTasks.addAll(managerTasks);
            combinedTasks.addAll(assigneeTasks);

            // Convert to paginated result
            int start = Math.min((int) pageable.getOffset(), combinedTasks.size());
            int end = Math.min(start + pageable.getPageSize(), combinedTasks.size());
            taskPage = new PageImpl<>(combinedTasks.subList(start, end), pageable, combinedTasks.size());
        }

        // Prepare response map
        Map<String, Object> response = new HashMap<>();
        response.put("tasks", taskPage.stream().map(this::mapToTaskResponse).toList());
        response.put("currentPage", taskPage.getNumber());
        response.put("totalItems", taskPage.getTotalElements());
        response.put("totalPages", taskPage.getTotalPages());
        response.put("isLastPage", taskPage.isLast());

        return response;
    }

    private TaskListResponse mapToTaskResponse(Task task) {
        TaskListResponse response = new TaskListResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus().getName());
        response.setStartDate(task.getStartDate());
        response.setDueDate(task.getDueDate());
        response.setCompletedDate(task.getCompletedDate());
        response.setCriticality(task.getCriticality());
        response.setManagerId(task.getManager() != null ? task.getManager().getId() : null);
        response.setManagerName(task.getManager() != null ? task.getManager().getUserName() : null);
        response.setAssigneeUserId(task.getAssignee() != null ? task.getAssignee().getId() : null);
        response.setAssigneeUserName(task.getAssignee() != null ? task.getAssignee().getUserName() : null);
        response.setMilestoneId(task.getMilestone().getId());
        response.setMilestoneName(task.getMilestone().getMileStoneName());
        response.setRemark(task.getRemark());
        response.setComplianceId(task.getMilestone().getCompliance().getId());
        response.setComplianceName(task.getMilestone().getCompliance().getComplianceName());

        return response;
    }

}

