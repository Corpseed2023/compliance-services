package com.lawzoom.complianceservice.dto.renewalDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RenewalResponse {

    private Long id;

    private Long milestoneId; // Optional, for milestone-related renewals

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private String reminderDurationType;

    private int reminderDurationValue;

    private LocalDate nextReminderDate;

    private String renewalNotes;

    private boolean stopFlag;

    private int reminderFrequency;
}
