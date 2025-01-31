package com.lawzoom.complianceservice.dto.taskDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskReminderResponse {
    private Long id;
    private LocalDate reminderDate;
    private LocalDate reminderEndDate;
    private int notificationTimelineValue;
    private int repeatTimelineValue;
    private String repeatTimelineType;
    private int stopFlag;
    private Long createdById;
    private String createdByName;
}
