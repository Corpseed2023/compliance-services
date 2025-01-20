package com.lawzoom.complianceservice.serviceImpl.taskServiceImpl;


import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.mileStoneTask.Task;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.StatusRepository;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public TaskListResponse createTask(TaskRequest taskRequest) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(taskRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + taskRequest.getMilestoneId()));

        // Validate Reporter User
        User reporter = userRepository.findById(taskRequest.getManagerId())
                .orElseThrow(() -> new NotFoundException("Reporter not found with ID: " + taskRequest.getManagerId()));

        // Validate Assignee User
        User assignee = userRepository.findById(taskRequest.getAssigneeId())
                .orElseThrow(() -> new NotFoundException("Assignee not found with ID: " + taskRequest.getAssigneeId()));

        // Validate Status
        Status status = statusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + taskRequest.getStatusId()));

        // Create Task Entity
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setCreatedAt(new Date());
        task.setUpdatedAt(new Date());
        task.setStatus(status);
        task.setStartDate(taskRequest.getStartDate());
        task.setDueDate(taskRequest.getDueDate());
        task.setCompletedDate(taskRequest.getCompletedDate());
        task.setCriticality(taskRequest.getCriticality());
        task.setMilestone(milestone);
        task.setManagerId(reporter);
        task.setAssigneeId(assignee);

        // Save Task
        Task savedTask = taskRepository.save(task);

        // Create TaskResponse and populate fields manually
        TaskListResponse response = new TaskListResponse();
        response.setId(savedTask.getId());
        response.setName(savedTask.getName());
        response.setDescription(savedTask.getDescription());
        response.setStatus(savedTask.getStatus().getName());
        response.setStartDate(savedTask.getStartDate());
        response.setDueDate(savedTask.getDueDate());
        response.setCompletedDate(savedTask.getCompletedDate());
        response.setCriticality(savedTask.getCriticality());
        response.setManagerId(savedTask.getManagerId().getId());
        response.setManagerName(savedTask.getManagerId().getUserName());
        response.setAssigneeUserId(savedTask.getAssigneeId().getId());
        response.setAssigneeUserName(savedTask.getAssigneeId().getUserName());
        response.setMilestoneId(savedTask.getMilestone().getId());
        response.setMilestoneName(savedTask.getMilestone().getMileStoneName());

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
            response.setManagerId(task.getManagerId().getId());
            response.setManagerName(task.getManagerId().getUserName());
            response.setAssigneeUserId(task.getAssigneeId().getId());
            response.setAssigneeUserName(task.getAssigneeId().getUserName());
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
        task.setAssigneeId(assignee);
        task.setManagerId(reporter);
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
        response.setManagerId(updatedTask.getManagerId().getId());
        response.setManagerName(updatedTask.getManagerId().getUserName());
        response.setAssigneeUserId(updatedTask.getAssigneeId().getId());
        response.setAssigneeUserName(updatedTask.getAssigneeId().getUserName());
        response.setMilestoneId(updatedTask.getMilestone().getId());
        response.setMilestoneName(updatedTask.getMilestone().getMileStoneName());

        return response;
    }



}

