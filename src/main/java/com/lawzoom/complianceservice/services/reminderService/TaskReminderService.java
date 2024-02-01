package com.lawzoom.complianceservice.services.reminderService;

import com.lawzoom.complianceservice.dto.reminderDto.TaskReminderRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface TaskReminderService {


    ResponseEntity<String> saveTaskReminder(TaskReminderRequest taskReminderRequest);

    ResponseEntity<String> updateTaskReminder(TaskReminderRequest taskReminderRequest,Long taskReminderId);
}
