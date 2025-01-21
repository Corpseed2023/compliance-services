package com.lawzoom.complianceservice.dto.renewalDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RenewalRequest {

    @NotNull
    private LocalDate issuedDate;

    @NotNull
    private LocalDate expiryDate;

    @NotBlank
    private String reminderDurationType;

    @NotNull
    private int reminderDurationValue;

    private String renewalNotes;

    private boolean stopFlag = false;
}
