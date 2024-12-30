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
public class GstDetailsResponse {
    private Long id;
    private String gstNumber;
    private Long companyId;
    private Long countryId;
    private String countryName;
    private Long stateId;
    private String stateName;
    private LocalDate gstRegistrationDate;
    private String companyName;
    private Long businessUnitCount;

}


