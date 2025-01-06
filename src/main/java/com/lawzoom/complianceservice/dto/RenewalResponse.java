package com.lawzoom.complianceservice.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class RenewalResponse {
    private Long id;
    private Long complianceId;
    private LocalDate nextRenewalDate;
    private int renewalFrequency;
    private String renewalType;
    private String renewalNotes;
    private boolean isActive;
}
