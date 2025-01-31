package com.lawzoom.complianceservice.service.taskService;


import com.lawzoom.complianceservice.dto.taskDto.TaskReminderUpdateRequest;

import java.util.Map;

public interface TaskReminderService {

    Map<String, Object> updateTaskReminder(Long taskReminderId, TaskReminderUpdateRequest updateRequest);
}
