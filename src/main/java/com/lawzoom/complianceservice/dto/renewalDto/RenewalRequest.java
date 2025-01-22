package com.lawzoom.complianceservice.dto.renewalDto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class RenewalRequest {

    private Long userId;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private String reminderDurationType;

    private int reminderDurationValue;

    private String renewalNotes;

    private boolean stopFlag = false;
}
