package com.lawzoom.complianceservice.service.taskService;


import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;

import java.util.List;

public interface TaskService {
    TaskListResponse createTask(TaskRequest taskRequest);
    List<TaskListResponse> fetchTasks(Long milestoneId);

    TaskListResponse updateTaskAssignment(Long taskId, Long assigneeUserId, Long reporterUserId);
}

