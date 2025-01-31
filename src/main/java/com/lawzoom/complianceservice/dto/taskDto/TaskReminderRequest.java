package com.lawzoom.complianceservice.dto.taskDto;


import jakarta.validation.constraints.Min;
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

    private LocalDate reminderDate;

    private LocalDate reminderEndDate;

    @Min(value = 0, message = "Notification timeline value must not be negative")
    private int notificationTimelineValue;

    @Min(value = 0, message = "Repeat timeline value must not be negative")
    private int repeatTimelineValue;

    private String repeatTimelineType;

    private int stopFlag = 1;
}