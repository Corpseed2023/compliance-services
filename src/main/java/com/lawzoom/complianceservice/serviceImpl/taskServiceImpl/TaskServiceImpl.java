package com.lawzoom.complianceservice.serviceImpl.taskServiceImpl;


import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.mileStoneTask.Task;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.StatusRepository;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Override
    public TaskListResponse createTask(TaskRequest taskRequest, Long userId) {

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

        // Save Task
        Task savedTask = taskRepository.save(task);

        // Map to Response
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
        return response;
    }



    @Override
    public List<TaskListResponse> fetchTasks(Long milestoneId) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Fetch Tasks
        List<Task> tasks = taskRepository.findByMilestone(milestone);

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



}

