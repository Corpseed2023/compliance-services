package com.lawzoom.complianceservice.dto.taskDto;

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
public class TaskRenewalRequest {

    private LocalDate issuedDate;
    private LocalDate expiryDate;

    @NotNull(message = "Renewal Date is required")
    private LocalDate renewalDate;

    @NotNull(message = "Reminder Duration Type is required")
    private String reminderDurationType;

    @NotNull(message = "Reminder Duration Value is required")
    private Integer reminderDurationValue;

    private String renewalNotes;

    private boolean notificationsEnabled = true;

    @NotNull(message = "Certificate Type Duration is required")
    private String certificateTypeDuration;

    @NotNull(message = "Certificate Duration Value is required")
    private Integer certificateDurationValue;
}
