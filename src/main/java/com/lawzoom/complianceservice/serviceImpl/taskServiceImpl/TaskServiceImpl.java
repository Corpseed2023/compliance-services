package com.lawzoom.complianceservice.serviceImpl.taskServiceImpl;

import com.lawzoom.complianceservice.dto.DocumentRequest;
import com.lawzoom.complianceservice.dto.taskDto.*;
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

        Subscriber subscriber = subscriberRepository.findById(taskRequest.getSubscriberId())
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
        task.setEnable(true);
        task.setSubscriber(subscriber);

        Task savedTask = taskRepository.save(task);

        // ✅ Handle optional documents
        if (taskRequest.getDocuments() != null && !taskRequest.getDocuments().isEmpty()) {
            for (DocumentRequest documentRequest : taskRequest.getDocuments()) {
                Document document = new Document();
                document.setTask(savedTask);
                document.setFile(documentRequest.getFile());
                documentRepository.save(document);
            }
        }

        if (taskRequest.getReminders() != null && !taskRequest.getReminders().isEmpty()) {
            for (TaskReminderRequest reminderRequest : taskRequest.getReminders()) {
                if (reminderRequest.getReminderDate() == null || reminderRequest.getReminderEndDate() == null) {
                    continue; // Skip invalid reminders
                }

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

        System.out.println("Task Created Successfully: " + savedTask.getId());

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
    public Map<String, Object> updateTask(TaskUpdateRequest taskUpdateRequest) {
        // Validate and fetch existing task
        Task task = taskRepository.findById(taskUpdateRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskUpdateRequest.getTaskId()));

        // Validate status
        Status status = statusRepository.findById(taskUpdateRequest.getStatusId())
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + taskUpdateRequest.getStatusId()));

        // Validate milestone
        MileStone milestone = milestoneRepository.findById(taskUpdateRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + taskUpdateRequest.getMilestoneId()));

        // Validate manager and assignee
        User manager = userRepository.findById(taskUpdateRequest.getManagerId())
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + taskUpdateRequest.getManagerId()));

        User assignee = userRepository.findById(taskUpdateRequest.getAssigneeId())
                .orElseThrow(() -> new NotFoundException("Assignee not found with ID: " + taskUpdateRequest.getAssigneeId()));

        // Validate subscriber
        Subscriber subscriber = subscriberRepository.findById(taskUpdateRequest.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + taskUpdateRequest.getSubscriberId()));

        // Update task details
        task.setName(taskUpdateRequest.getName());
        task.setDescription(taskUpdateRequest.getDescription());
        task.setStatus(status);
        task.setStartDate(taskUpdateRequest.getStartDate());
        task.setDueDate(taskUpdateRequest.getDueDate());
        task.setCompletedDate(taskUpdateRequest.getCompletedDate());
        task.setCriticality(taskUpdateRequest.getCriticality());
        task.setRemark(taskUpdateRequest.getRemark());
        task.setManager(manager);
        task.setAssignee(assignee);
        task.setMilestone(milestone);
        task.setSubscriber(subscriber);
        task.setUpdatedAt(new Date());

        // Save updated task
        Task updatedTask = taskRepository.save(task);

        // Update documents if provided
        if (taskUpdateRequest.getDocuments() != null) {
            documentRepository.deleteByTaskId(updatedTask.getId()); // Remove existing documents
            for (DocumentRequest docReq : taskUpdateRequest.getDocuments()) {
                Document document = new Document();
                document.setTask(updatedTask);
                document.setFile(docReq.getFile());
                documentRepository.save(document);
            }
        }

        if (taskUpdateRequest.getReminders() != null) {
            taskReminderRepository.deleteByTaskId(updatedTask.getId()); // Remove existing reminders
            for (TaskReminderRequest reminderReq : taskUpdateRequest.getReminders()) {
                TaskReminder reminder = new TaskReminder();
                reminder.setTask(updatedTask);
                reminder.setCreatedBy(manager);
                reminder.setReminderDate(reminderReq.getReminderDate());
                reminder.setReminderEndDate(reminderReq.getReminderEndDate());
                reminder.setNotificationTimelineValue(reminderReq.getNotificationTimelineValue());
                reminder.setRepeatTimelineValue(reminderReq.getRepeatTimelineValue());
                reminder.setRepeatTimelineType(reminderReq.getRepeatTimelineType());
                taskReminderRepository.save(reminder);
            }
        }

        // Create response map
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Task updated successfully");
        response.put("taskId", updatedTask.getId());
        response.put("name", updatedTask.getName());
        response.put("status", updatedTask.getStatus().getName());
        response.put("startDate", updatedTask.getStartDate());
        response.put("dueDate", updatedTask.getDueDate());
        response.put("completedDate", updatedTask.getCompletedDate());
        response.put("criticality", updatedTask.getCriticality());
        response.put("remark", updatedTask.getRemark());
        response.put("managerName", updatedTask.getManager().getUserName());
        response.put("assigneeName", updatedTask.getAssignee().getUserName());
        response.put("milestoneName", updatedTask.getMilestone().getMileStoneName());

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

