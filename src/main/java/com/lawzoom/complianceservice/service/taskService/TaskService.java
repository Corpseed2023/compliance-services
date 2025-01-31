package com.lawzoom.complianceservice.service.taskService;


import com.lawzoom.complianceservice.dto.taskDto.TaskListResponse;
import com.lawzoom.complianceservice.dto.taskDto.TaskRequest;
import com.lawzoom.complianceservice.dto.taskDto.TaskUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TaskService {
    TaskListResponse createTask(TaskRequest taskRequest,Long userid);
    List<TaskListResponse> fetchTasks(Long milestoneId);


    Map<String, Object> fetchAllTask(Long userId, Long subscriberId, Pageable pageable);

    Map<String, Object> updateTask(TaskUpdateRequest taskUpdateRequest);
}

