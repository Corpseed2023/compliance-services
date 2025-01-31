package com.lawzoom.complianceservice.dto.complianceReminder;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReminderResponse {

    private Long id;
    private Long milestoneId;
    private Long subscriberId;
    private LocalDate reminderDate;
    private LocalDate reminderEndDate;
    private Integer notificationTimelineValue;
    private Integer repeatTimelineValue;
    private String repeatTimelineType;
    private Integer stopFlag;
    private Long userId;
}
