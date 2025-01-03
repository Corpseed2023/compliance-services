package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.dto.complianceReminder.ComplianceReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ComplianceReminderResponse;

import java.util.List;

public interface ComplianceReminderService {
    ComplianceReminderResponse createComplianceReminder(Long complianceId, Long subscriberId, ComplianceReminderRequest reminderRequest);

    List<ComplianceReminderResponse> fetchComplianceReminders(Long complianceId, Long subscriberId);
}
