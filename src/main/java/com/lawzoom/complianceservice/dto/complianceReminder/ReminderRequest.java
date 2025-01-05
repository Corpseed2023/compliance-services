package com.lawzoom.complianceservice.dto.complianceReminder;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Data
public class ReminderRequest {

    @NotNull(message = "Reminder date is required")
    private Date reminderDate;

    @NotNull(message = "Reminder end date is required")
    private Date reminderEndDate;

    @Min(value = 1, message = "Notification timeline value must be at least 1")
    private int notificationTimelineValue;

    @Min(value = 0, message = "Repeat timeline value must not be negative")
    private int repeatTimelineValue;

    @NotBlank(message = "Repeat timeline type is required")
    private String repeatTimelineType;

    @NotNull(message = "Created by user ID is required")
    private Long createdBy;

    @NotNull(message = "Recipient user ID is required")
    private Long whomToSend;

    private boolean isEnable;
}
