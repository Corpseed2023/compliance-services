package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;
import java.util.List;
import java.util.Map;

public interface ReminderService {
    ReminderResponse createOrUpdateReminder(ReminderRequest reminderRequest);

    Map<String, Object> fetchAllRemindersByUserId(Long userId);



}
