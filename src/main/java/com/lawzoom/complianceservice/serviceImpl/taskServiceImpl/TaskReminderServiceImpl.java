package com.lawzoom.complianceservice.serviceImpl.taskServiceImpl;


import com.lawzoom.complianceservice.dto.taskDto.TaskReminderUpdateRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;

import com.lawzoom.complianceservice.repository.TaskReminderRepository;
import com.lawzoom.complianceservice.service.taskService.TaskReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskReminderServiceImpl implements TaskReminderService {

    @Autowired
    private TaskReminderRepository taskReminderRepository;

    @Override
    @Transactional
    public Map<String, Object> updateTaskReminder(Long taskReminderId, TaskReminderUpdateRequest updateRequest) {
        // Step 1: Fetch existing Task Reminder
        TaskReminder taskReminder = taskReminderRepository.findById(taskReminderId)
                .orElseThrow(() -> new NotFoundException("Task Reminder not found with ID: " + taskReminderId));

        // Step 2: Update fields
        taskReminder.setReminderDate(updateRequest.getReminderDate());
        taskReminder.setReminderEndDate(updateRequest.getReminderEndDate());
        taskReminder.setNotificationTimelineValue(updateRequest.getNotificationTimelineValue());
        taskReminder.setRepeatTimelineValue(updateRequest.getRepeatTimelineValue());
        taskReminder.setRepeatTimelineType(updateRequest.getRepeatTimelineType());
        taskReminder.setStopFlag(updateRequest.getStopFlag());

        // Step 3: Save updated Task Reminder
        taskReminder = taskReminderRepository.save(taskReminder);

        // Step 4: Prepare response map
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task Reminder updated successfully");
        response.put("taskReminderId", taskReminder.getId());
        response.put("reminderDate", taskReminder.getReminderDate());
        response.put("reminderEndDate", taskReminder.getReminderEndDate());
        response.put("notificationTimelineValue", taskReminder.getNotificationTimelineValue());
        response.put("repeatTimelineValue", taskReminder.getRepeatTimelineValue());
        response.put("repeatTimelineType", taskReminder.getRepeatTimelineType());
        response.put("stopFlag", taskReminder.getStopFlag());

        return response;
    }
}
