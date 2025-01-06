package com.lawzoom.complianceservice.dto.companyDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CompanyResponseDetails {

    private Long companyId;

    private String companyName;

    private String registrationNumber;

    private LocalDate registrationDate;

    private String state;

    private int businessUnitCount;

    private int numberOfStatesCount;

    private int gstRegistrationCount;


}
