package com.lawzoom.complianceservice.service;



import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;

import java.util.List;

public interface ReminderService {
    ReminderResponse createReminder(Long complianceId, Long subscriberId, ReminderRequest reminderRequest);

    List<ReminderResponse> fetchReminders(Long complianceId, Long subscriberId);
}
