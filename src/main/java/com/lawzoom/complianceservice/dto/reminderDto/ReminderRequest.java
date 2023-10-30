package com.lawzoom.complianceservice.dto.reminderDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReminderRequest {
    private Long id;
    private Long complianceId;
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

    // Constructors, getters, and setters

    public ReminderRequest() {
    }

    public ReminderRequest(Long id, Long complianceId, Date reminderDate, int notificationTimelineValue, String notificationTimelineType, int repeatTimelineValue, String repeatTimelineType, String repeatOnDay, Date reminderEndDate, Date createdAt, Date updatedAt, boolean isEnable) {
        this.id = id;
        this.complianceId = complianceId;
        this.reminderDate = reminderDate;
        this.notificationTimelineValue = notificationTimelineValue;
        this.notificationTimelineType = notificationTimelineType;
        this.repeatTimelineValue = repeatTimelineValue;
        this.repeatTimelineType = repeatTimelineType;
        this.repeatOnDay = repeatOnDay;
        this.reminderEndDate = reminderEndDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnable = isEnable;
    }



}
