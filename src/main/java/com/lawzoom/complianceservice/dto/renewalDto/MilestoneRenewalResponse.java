package com.lawzoom.complianceservice.dto.renewalDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MilestoneRenewalResponse {

    private Long id;

    private Long milestoneId;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private String reminderDurationType;

    private int reminderDurationValue;

    private LocalDate nextReminderDate;

    private String renewalNotes;

    private boolean notificationsEnabled;

    private int reminderFrequency;
}
