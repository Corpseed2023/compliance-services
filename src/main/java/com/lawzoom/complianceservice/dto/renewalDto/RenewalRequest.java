package com.lawzoom.complianceservice.dto.renewalDto;


import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RenewalRequest {

    private Long userId;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private LocalDate renewalDate;

    private String reminderDurationType;

    private int reminderDurationValue;

    private String renewalNotes;

    private boolean notificationsEnabled = false;
}
