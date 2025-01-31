package com.lawzoom.complianceservice.dto.renewalDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "DAYS|WEEKS|MONTHS|YEARS", message = "Reminder duration type must be one of: DAYS, WEEKS, MONTHS, YEARS")
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
