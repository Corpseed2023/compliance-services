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

    private LocalDate renewalDate;

    @NotNull(message = "Reminder duration type is required")
    private String reminderDurationType;

    private Integer reminderDurationValue;

    private String renewalNotes;

    private boolean notificationsEnabled = false;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Certificate type duration is required")
    private String certificateTypeDuration;

    @NotNull(message = "Certificate duration value is required")
    private Integer certificateDurationValue;
}
