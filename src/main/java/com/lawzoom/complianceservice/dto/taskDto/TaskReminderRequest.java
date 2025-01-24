package com.lawzoom.complianceservice.dto.taskDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskReminderRequest {

    @NotNull(message = "Reminder Date is required")
    private LocalDate reminderDate;

    @NotNull(message = "Reminder End Date is required")
    private LocalDate reminderEndDate;

    @Min(value = 0, message = "Notification timeline value must not be negative")
    private int notificationTimelineValue;

    @Min(value = 0, message = "Repeat timeline value must not be negative")
    private int repeatTimelineValue;

    @NotNull(message = "Repeat Timeline Type is required")
    private String repeatTimelineType;

    private int stopFlag = 1;
}
