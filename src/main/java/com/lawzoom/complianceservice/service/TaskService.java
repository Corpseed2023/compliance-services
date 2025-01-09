package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.TaskRequest;
import com.lawzoom.complianceservice.dto.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);
    List<TaskResponse> fetchTasks(Long milestoneId);

    TaskResponse updateTaskAssignment(Long taskId, Long assigneeUserId, Long reporterUserId);
}

