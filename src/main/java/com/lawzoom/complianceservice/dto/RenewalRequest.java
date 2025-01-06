package com.lawzoom.complianceservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RenewalRequest {
    @NotNull
    private LocalDate nextRenewalDate;

    @NotNull
    private int renewalFrequency;

    @NotBlank
    private String renewalType;

    private String renewalNotes;

    private boolean isActive;
}
