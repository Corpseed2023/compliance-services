package com.lawzoom.complianceservice.dto.reminderDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReminderRequest {

    private Long complianceId;
    private Long taskId;
    private Long subTaskId;
      private Date reminderDate;
    private int notificationTimelineValue;
    private String notificationTimelineType;
    private int repeatTimelineValue;
    private String repeatTimelineType;
    private String repeatOnDay;
    private Date reminderEndDate;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;


}
