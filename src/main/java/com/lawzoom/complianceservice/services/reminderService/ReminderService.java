package com.lawzoom.complianceservice.services.reminderService;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface ReminderService {
    ResponseEntity saveReminder(Reminder reminder);

    ResponseEntity updateReminder(Reminder reminder);

    ResponseEntity fetchReminder(Long id);

    ResponseEntity deleteReminder(Long id);
}
