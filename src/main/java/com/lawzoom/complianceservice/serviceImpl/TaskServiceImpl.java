package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.TaskRequest;
import com.lawzoom.complianceservice.dto.TaskResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.mileStoneTask.Task;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.TaskRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(taskRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + taskRequest.getMilestoneId()));

        // Validate Reporter User
        User reporter = userRepository.findById(taskRequest.getReporterUserId())
                .orElseThrow(() -> new NotFoundException("Reporter not found with ID: " + taskRequest.getReporterUserId()));

        // Validate Assignee User
        User assignee = userRepository.findById(taskRequest.getAssigneeUserId())
                .orElseThrow(() -> new NotFoundException("Assignee not found with ID: " + taskRequest.getAssigneeUserId()));

        // Create Task Entity
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
//        task.setTimelineValue(taskRequest.getTimelineValue());
//        task.setTimelineType(taskRequest.getTimelineType());
        task.setCreatedAt(new Date());
        task.setUpdatedAt(new Date());
        task.setStartDate(taskRequest.getStartDate());
        task.setDueDate(taskRequest.getDueDate());
        task.setCompletedDate(taskRequest.getCompletedDate());
        task.setCriticality(taskRequest.getCriticality());
        task.setMilestone(milestone);
        task.setReporterUserId(reporter);
        task.setAssigneeUserId(assignee);

        // Save Task
        Task savedTask = taskRepository.save(task);

        // Map to Response DTO
        return mapToTaskResponse(savedTask);
    }

    @Override
    public List<TaskResponse> fetchTasks(Long milestoneId) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Fetch Tasks
        List<Task> tasks = taskRepository.findByMilestone(milestone);

        // Map Tasks to Response DTOs
        return tasks.stream().map(this::mapToTaskResponse).collect(Collectors.toList());
    }

    private TaskResponse mapToTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
//        response.setTimelineValue(task.getTimelineValue());
//        response.setTimelineType(task.getTimelineType());
        response.setStatus(task.getStatus().toString());
        response.setStartDate(task.getStartDate());
        response.setDueDate(task.getDueDate());
        response.setCompletedDate(task.getCompletedDate());
        response.setCriticality(task.getCriticality());
        response.setReporterUserId(task.getReporterUserId().getId());
        response.setReporterUserName(task.getReporterUserId().getUserName());
        response.setAssigneeUserId(task.getAssigneeUserId().getId());
        response.setAssigneeUserName(task.getAssigneeUserId().getUserName());
        return response;
    }
}

