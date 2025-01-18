package com.lawzoom.complianceservice.dto.complianceReminder;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ReminderResponse {

    private Long id;
    private Long complianceId;
    private Long subscriberId;
    private Long superAdminId;
    private Long createdBy;
    private Long whomToSend;
    private LocalDate reminderDate;
    private LocalDate reminderEndDate;
    private int notificationTimelineValue;
    private int repeatTimelineValue;
    private String repeatTimelineType;
    private boolean isEnable;
}
