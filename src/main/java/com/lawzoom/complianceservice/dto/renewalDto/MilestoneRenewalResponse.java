package com.lawzoom.complianceservice.dto.renewalDto;


import lombok.Data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MilestoneRenewalResponse {
    private Long id;
    private Long milestoneId;
    private LocalDate nextRenewalDate;
    private int renewalFrequency;
    private String renewalType;
    private String renewalNotes;
    private boolean stopFlag;
}
