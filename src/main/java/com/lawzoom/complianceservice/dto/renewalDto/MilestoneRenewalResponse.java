package com.lawzoom.complianceservice.dto.renewalDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private String certificateTypeDuration;

    private int certificateDurationValue;
}
