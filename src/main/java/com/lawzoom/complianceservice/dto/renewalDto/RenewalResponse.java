package com.lawzoom.complianceservice.dto.renewalDto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RenewalResponse {

    private Long id;

    private Long milestoneId;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private String reminderDurationType;

    private int reminderDurationValue;

    private LocalDate nextReminderDate;

    private String renewalNotes;

    private boolean stopFlag;

    private int reminderFrequency;
}
