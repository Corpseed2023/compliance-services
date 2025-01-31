package com.lawzoom.complianceservice.dto.taskDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TaskReminderUpdateRequest {

    @NotNull(message = "Reminder date is required")
    private LocalDate reminderDate;

    @NotNull(message = "Reminder end date is required")
    private LocalDate reminderEndDate;

    @Min(value = 0, message = "Notification timeline value must not be negative")
    private int notificationTimelineValue;

    @Min(value = 0, message = "Repeat timeline value must not be negative")
    private int repeatTimelineValue;

    @NotNull(message = "Repeat Timeline Type is required")
    private String repeatTimelineType;

    private int stopFlag;
}
