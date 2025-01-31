package com.lawzoom.complianceservice.dto.renewalDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RenewalRequest {

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    @NotNull(message = "Renewal Date is required")
    private LocalDate renewalDate;

    @NotNull(message = "Reminder Duration Type is required")
    private String reminderDurationType;

    @NotNull(message = "Reminder Duration Value is required")
    private Integer reminderDurationValue;

    private String renewalNotes;

    private boolean notificationsEnabled = false;

    private Long userId;

    @NotNull(message = "Certificate Type Duration is required")
    private String certificateTypeDuration;

    @NotNull(message = "Certificate Duration Value is required")
    private Integer certificateDurationValue;

}
