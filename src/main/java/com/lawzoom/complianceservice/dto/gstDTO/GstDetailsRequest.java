package com.lawzoom.complianceservice.dto.gstDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GstDetailsRequest {

    private String gstNumber;
    private Long companyId;
    private Long countryId;
    private Long stateId;
    private LocalDate gstRegistrationDate;
    private Long subscriberId;
    private Long userId;
}

